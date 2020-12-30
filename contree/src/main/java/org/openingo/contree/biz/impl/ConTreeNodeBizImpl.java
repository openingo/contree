/*
 * MIT License
 *
 * Copyright (c) 2020 OpeningO Co.,Ltd.
 *
 *    https://openingo.org
 *    contactus(at)openingo.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.openingo.contree.biz.impl;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openingo.contree.base.entity.ConTreeNodeDO;
import org.openingo.contree.biz.IConTreeNodeBiz;
import org.openingo.contree.bo.NodeExtensionObj;
import org.openingo.contree.constants.DeleteMode;
import org.openingo.contree.constants.FetchType;
import org.openingo.contree.entity.ConTreeNode;
import org.openingo.contree.service.IConTreeNodeNotifyService;
import org.openingo.contree.service.IConTreeNodeService;
import org.openingo.contree.vo.ConTreeNodeVO;
import org.openingo.contree.vo.list.ConTreeNodeListReqVO;
import org.openingo.contree.vo.list.ConTreeNodeListRespVO;
import org.openingo.contree.vo.reorder.ConTreeNodeReorderItemVO;
import org.openingo.contree.vo.reorder.ConTreeNodeReorderVO;
import org.openingo.jdkits.collection.ListKit;
import org.openingo.jdkits.json.JacksonKit;
import org.openingo.jdkits.validate.ValidateKit;
import org.openingo.spring.exception.ServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ConTreeNodeBizImpl
 *
 * @author Qicz
 */
@Service
@Slf4j
public class ConTreeNodeBizImpl implements IConTreeNodeBiz {

    @Autowired
    private IConTreeNodeService conTreeNodeService;

    @Autowired
    private IConTreeNodeNotifyService conTreeNodeNotifyService;

    /**
     * 更新或新增节点
     * @param conTreeNodeVO 节点信息
     * @return true成功false失败
     */
    @SneakyThrows
    private boolean saveOrUpdateNode(ConTreeNodeVO conTreeNodeVO) {
        ConTreeNodeDO conTreeNodeDO = new ConTreeNodeDO();
        Object nodeExtension = conTreeNodeVO.getNodeExtension();
        BeanUtils.copyProperties(conTreeNodeVO, conTreeNodeDO);
        if (ValidateKit.isNotNull(nodeExtension)) {
            nodeExtension = JacksonKit.toJson(NodeExtensionObj.object(nodeExtension));
            conTreeNodeDO.setNodeExtension(nodeExtension.toString());
        }
        boolean ret = this.conTreeNodeService.saveOrUpdate(conTreeNodeDO);
        // 回写nodeId
        conTreeNodeVO.setNodeId(conTreeNodeDO.getNodeId());
        return ret;
    }

    /**
     * 校验Node
     * @param treeCode 树编码
     * @param nodeId 节点Id
     */
    private ConTreeNodeDO validateNode(String treeCode, Integer nodeId) {
        ConTreeNodeDO nodeDO = this.conTreeNodeService.getById(nodeId);
        boolean check = ValidateKit.isNull(nodeDO) || !treeCode.equals(nodeDO.getTreeCode());
        if (check) {
            throw new ServiceException("数据不合法[treeCode与nodeId不匹配，数据可能不存在].");
        }
        return nodeDO;
    }

    /**
     * 转为Node
     * @param conTreeNodeVO conTreeNodeVO
     * @return a converted node
     */
    private ConTreeNode toNode(ConTreeNodeVO conTreeNodeVO) {
        return new ConTreeNode(conTreeNodeVO.getNodeId(), conTreeNodeVO.getNodeExtension());
    }

    /**
     * 添加节点
     *
     * @param conTreeNodeVO 添加信息
     * @return true成功false失败
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addNode(ConTreeNodeVO conTreeNodeVO) {
        boolean ret = this.saveOrUpdateNode(conTreeNodeVO);
        if (!ret) {
            log.info("==添加节点失败。(不进行变动通知)==");
            throw new ServiceException("节点添加失败，请稍后再试!");
        }
        this.reorderNodes(conTreeNodeVO.getReorderNodes());
        this.conTreeNodeNotifyService.addNode(conTreeNodeVO.getTreeCode(), this.toNode(conTreeNodeVO));
        return true;
    }

    /**
     * 编辑节点
     *
     * @param conTreeNodeVO 编辑信息
     * @return true成功false失败
     */
    @SneakyThrows
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean editNode(ConTreeNodeVO conTreeNodeVO) {
        String treeCode = conTreeNodeVO.getTreeCode();
        ConTreeNodeDO treeNodeDO = this.validateNode(treeCode, conTreeNodeVO.getNodeId());
        boolean ret = this.saveOrUpdateNode(conTreeNodeVO);
        if (!ret) {
            log.info("==编辑节点失败。(不进行变动通知)==");
            throw new ServiceException("编辑节点失败，请稍后再试!");
        }
        // json to obj
        String nodeExtension = treeNodeDO.getNodeExtension();
        // 此次未变更nodeExtension，使用历史数据
        if (ValidateKit.isNull(conTreeNodeVO.getNodeExtension())
                && ValidateKit.isNotNull(nodeExtension)) {
            NodeExtensionObj nodeExtensionObj = JacksonKit.toObj(nodeExtension, NodeExtensionObj.class);
            conTreeNodeVO.setNodeExtension(nodeExtensionObj.getObject());
        }
        this.conTreeNodeNotifyService.editNode(treeCode, this.toNode(conTreeNodeVO));
        return true;
    }

    /**
     * 删除节点
     * @param conTreeNodeVO 删除信息
     * @return true成功false失败
     */
    @SneakyThrows
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteNode(ConTreeNodeVO conTreeNodeVO) {
        boolean ret = false;
        String treeCode = conTreeNodeVO.getTreeCode();
        Integer rootNodeId = conTreeNodeVO.getRootNodeId();
        // 校验是否合法
        this.validateNode(treeCode, rootNodeId);
        // 获取节点数据
        boolean recursion = DeleteMode.CASCADE.equals(conTreeNodeVO.getMode());
        List<ConTreeNodeDO> listNodes = this.conTreeNodeService.listNodes(treeCode, rootNodeId, recursion);

        List<ConTreeNode> deleteNodes = ListKit.emptyArrayList();
        // 判断是否进行删除操作
        if (ValidateKit.isNotEmpty(listNodes)) {
            // 非级联删除，且有子节点时不可删除：non-sons
            if (!recursion) {
                throw new ServiceException(String.format("找到%d个子节点，不可删除.", listNodes.size()));
            }
            List<Integer> ids = listNodes.stream().map(ConTreeNodeDO::getNodeId).collect(Collectors.toList());
            if (ValidateKit.isNotEmpty(ids)) {
                ret = this.conTreeNodeService.removeByIds(ids);
            }
            if (!ret) {
                log.info("==删除的节点失败。(不进行变动通知)==");
                throw new ServiceException("删除的节点失败，请稍后再试!");
            }

            for (ConTreeNodeDO listNode : listNodes) {
                String nodeExtension = listNode.getNodeExtension();
                Object obj = null;
                if (ValidateKit.isNotNull(nodeExtension)) {
                    NodeExtensionObj nodeExtensionObj = JacksonKit.toObj(nodeExtension, NodeExtensionObj.class);
                    obj = nodeExtensionObj.getObject();
                }
                deleteNodes.add(new ConTreeNode(listNode.getNodeId(), obj));
            }
        }
        // 通知业务进行后续处理
        this.conTreeNodeNotifyService.deleteNode(treeCode, deleteNodes);
        return true;
    }

    private boolean reorderNodes(List<ConTreeNodeReorderItemVO> reorderNodes) {
        if (ValidateKit.isNull(reorderNodes)) {
            return true;
        }
        List<ConTreeNodeDO> treeNodes = ListKit.emptyArrayList();
        reorderNodes.forEach(item -> {
            ConTreeNodeDO conTreeNodeDO = new ConTreeNodeDO();
            conTreeNodeDO.setNodeId(item.getNodeId());
            Integer rootNodeId = item.getRootNodeId();
            if (ValidateKit.isNotNull(rootNodeId)) {
                conTreeNodeDO.setRootNodeId(rootNodeId);
            }
            conTreeNodeDO.setNodeOrder(item.getNodeOrder());
            treeNodes.add(conTreeNodeDO);
        });
        return this.conTreeNodeService.updateBatchById(treeNodes);
    }

    /**
     * 节点重排序
     * TODO 校验数据合法性 String treeCode = conTreeNodeReorderVO.getTreeCode();
     * @param conTreeNodeReorderVO 节点重排序信息
     * @return true成功false失败
     */
    @Override
    public boolean reorderNodes(ConTreeNodeReorderVO conTreeNodeReorderVO) {
        return this.reorderNodes(conTreeNodeReorderVO.getReorderNodes());
    }

    /**
     * 获取树节点列表
     *
     * @param conTreeNodeListReqVO 请求参数
     * @return 树结构
     */
    @SneakyThrows
    @Override
    public ConTreeNodeListRespVO listNodes(ConTreeNodeListReqVO conTreeNodeListReqVO) {
        String treeCode = conTreeNodeListReqVO.getTreeCode();
        Integer rootNodeId = conTreeNodeListReqVO.getRootNodeId();
        String nodeName = conTreeNodeListReqVO.getNodeName();
        List<ConTreeNodeDO> listNodes = this.conTreeNodeService.listNodes(treeCode, rootNodeId, nodeName,
                FetchType.FULL.equals(conTreeNodeListReqVO.getFetchType()));
        // 转为为vo
        ConTreeNodeListRespVO listRespVO = new ConTreeNodeListRespVO();
        List<ConTreeNodeListRespVO.ConTreeNodeRespVO> treeRespNodes = ListKit.emptyArrayList();
        for (ConTreeNodeDO conTreeNodeDO : listNodes) {
            ConTreeNodeListRespVO.ConTreeNodeRespVO respVO = new ConTreeNodeListRespVO.ConTreeNodeRespVO();
            BeanUtils.copyProperties(conTreeNodeDO, respVO);
            // json to obj
            String nodeExtension = conTreeNodeDO.getNodeExtension();
            if (ValidateKit.isNotNull(nodeExtension)) {
                NodeExtensionObj nodeExtensionObj = JacksonKit.toObj(nodeExtension, NodeExtensionObj.class);
                respVO.setNodeExtension(nodeExtensionObj.getObject());
            }
            treeRespNodes.add(respVO);
        }
        listRespVO.setNodes(treeRespNodes);
        // 重绘制数据
        listRespVO.redraw(ValidateKit.isEmpty(nodeName), rootNodeId);
        return listRespVO;
    }
}

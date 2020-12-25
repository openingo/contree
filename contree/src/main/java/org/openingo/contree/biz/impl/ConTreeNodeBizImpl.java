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
import org.openingo.contree.base.entity.ConTreeNodeDO;
import org.openingo.contree.biz.IConTreeNodeBiz;
import org.openingo.contree.bo.NodeExtensionObj;
import org.openingo.contree.constants.DeleteMode;
import org.openingo.contree.constants.FetchType;
import org.openingo.contree.service.IConTreeNodeService;
import org.openingo.contree.vo.ConTreeNodeReorderVO;
import org.openingo.contree.vo.ConTreeNodeVO;
import org.openingo.contree.vo.list.ConTreeNodeListReqVO;
import org.openingo.contree.vo.list.ConTreeNodeListRespVO;
import org.openingo.jdkits.collection.ListKit;
import org.openingo.jdkits.json.JacksonKit;
import org.openingo.jdkits.validate.ValidateKit;
import org.openingo.spring.exception.ServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ConTreeNodeBizImpl
 *
 * @author Qicz
 */
@Service
public class ConTreeNodeBizImpl implements IConTreeNodeBiz {

    @Autowired
    private IConTreeNodeService conTreeNodeService;

    /**
     * 更新或新增节点
     * @param conTreeNodeVO 节点信息
     * @return true成功false失败
     */
    @SneakyThrows
    private boolean saveOrUpdateNode(ConTreeNodeVO conTreeNodeVO) {
        ConTreeNodeDO conTreeNodeDO = new ConTreeNodeDO();
        Object nodeExtension = conTreeNodeVO.getNodeExtension();
        if (ValidateKit.isNotNull(nodeExtension)) {
            conTreeNodeVO.setNodeExtension(JacksonKit.toJson(NodeExtensionObj.object(nodeExtension)));
        }
        BeanUtils.copyProperties(conTreeNodeVO, conTreeNodeDO);
        return this.conTreeNodeService.saveOrUpdate(conTreeNodeDO);
    }

    /**
     * 校验Node
     * @param treeCode 树编码
     * @param nodeId 节点Id
     */
    private void validateNode(String treeCode, Integer nodeId) {
        if (!this.conTreeNodeService.isValidNode(treeCode, nodeId)) {
            throw new ServiceException("数据不合法[treeCode与nodeId不匹配，数据可能不存在].");
        }
    }

    /**
     * 添加节点
     *
     * @param conTreeNodeVO 添加信息
     * @return true成功false失败
     */
    @Override
    public boolean addNode(ConTreeNodeVO conTreeNodeVO) {
        return this.saveOrUpdateNode(conTreeNodeVO);
    }

    /**
     * 编辑节点
     *
     * @param conTreeNodeVO 编辑信息
     * @return true成功false失败
     */
    @Override
    public boolean editNode(ConTreeNodeVO conTreeNodeVO) {
        this.validateNode(conTreeNodeVO.getTreeCode(), conTreeNodeVO.getNodeId());
        return this.saveOrUpdateNode(conTreeNodeVO);
    }

    /**
     * 删除节点
     * @param conTreeNodeVO 删除信息
     * @return true成功false失败
     */
    @Override
    public boolean deleteNode(ConTreeNodeVO conTreeNodeVO) {
        boolean ret = false;
        List<ConTreeNodeDO> listNodes = null;
        String treeCode = conTreeNodeVO.getTreeCode();
        Integer rootNodeId = conTreeNodeVO.getRootNodeId();
        if (DeleteMode.CASCADE.equals(conTreeNodeVO.getMode())) {
            // 级联删除直接删除
            listNodes = this.conTreeNodeService.listNodes(treeCode, rootNodeId, null, true);
            List<Integer> ids = listNodes.stream().map(ConTreeNodeDO::getNodeId).collect(Collectors.toList());
            if (ValidateKit.isNotEmpty(ids)) {
                ret = this.conTreeNodeService.removeByIds(ids);
            }
        } else {
            // 查找是不是存在子节点，如果有则不能删除
            listNodes = this.conTreeNodeService.listNodes(treeCode, rootNodeId, null,false);
            if (ValidateKit.isNotEmpty(listNodes)) {
                throw new ServiceException(String.format("找到%d个子节点，不可删除.", listNodes.size()));
            }
        }
        return ret;
    }

    /**
     * 节点重排序
     * TODO 校验数据合法性 String treeCode = conTreeNodeReorderVO.getTreeCode();
     * @param conTreeNodeReorderVO 节点重排序信息
     * @return true成功false失败
     */
    @Override
    public boolean reorderNodes(ConTreeNodeReorderVO conTreeNodeReorderVO) {
        List<ConTreeNodeDO> treeNodes = ListKit.emptyArrayList();
        // getReorderNodes 已在vo中校验，不可能为null或empty
        conTreeNodeReorderVO.getReorderNodes().forEach(item -> {
            ConTreeNodeDO conTreeNodeDO = new ConTreeNodeDO();
            conTreeNodeDO.setNodeId(item.getNodeId());
            conTreeNodeDO.setNodeOrder(item.getNodeOrder());
            treeNodes.add(conTreeNodeDO);
        });
        return this.conTreeNodeService.updateBatchById(treeNodes);
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
        String fetchType = conTreeNodeListReqVO.getFetchType();
        // 不为sons都需要递归查询
        List<ConTreeNodeDO> listNodes = this.conTreeNodeService.listNodes(treeCode, rootNodeId, nodeName, FetchType.FULL.equals(fetchType));
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

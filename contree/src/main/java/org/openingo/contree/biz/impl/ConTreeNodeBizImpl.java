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
import org.openingo.contree.service.IConTreeNodeService;
import org.openingo.contree.vo.ConTreeNodeReorderVO;
import org.openingo.contree.vo.ConTreeNodeVO;
import org.openingo.contree.vo.list.ConTreeNodeListReqVO;
import org.openingo.contree.vo.list.ConTreeNodeListRespVO;
import org.openingo.jdkits.collection.ListKit;
import org.openingo.spring.exception.ServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    private boolean saveOrUpdateNode(ConTreeNodeVO conTreeNodeVO) {
        ConTreeNodeDO conTreeNodeDO = new ConTreeNodeDO();
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
    @SneakyThrows
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
     * 删除节点 TODO
     *
     * @param treeCode 树编码
     * @param nodeId   待删除的id
     * @return true成功false失败
     */
    @Override
    public boolean deleteNode(String treeCode, Integer nodeId) {
        return false;
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
    @Override
    public ConTreeNodeListRespVO listNode(ConTreeNodeListReqVO conTreeNodeListReqVO) {
        return null;
    }
}

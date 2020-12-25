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

package org.openingo.contree.biz;

import org.openingo.contree.vo.ConTreeNodeReorderVO;
import org.openingo.contree.vo.ConTreeNodeVO;
import org.openingo.contree.vo.list.ConTreeNodeListReqVO;
import org.openingo.contree.vo.list.ConTreeNodeListRespVO;

/**
 * IConTreeNodeBiz
 *
 * @author Qicz
 */
public interface IConTreeNodeBiz {

    /**
     * 添加节点
     * @param conTreeNodeVO 添加信息
     * @return true成功false失败
     */
    boolean addNode(ConTreeNodeVO conTreeNodeVO);

    /**
     * 编辑节点
     * @param conTreeNodeVO 编辑信息
     * @return true成功false失败
     */
    boolean editNode(ConTreeNodeVO conTreeNodeVO);

    /**
     * 删除节点
     * @param treeCode 树编码
     * @param nodeId 待删除的id
     * @return true成功false失败
     */
    boolean deleteNode(String treeCode, Integer nodeId);

    /**
     * 节点重排序
     * @param conTreeNodeReorderVO 节点重排序信息
     * @return true成功false失败
     */
    boolean reorderNodes(ConTreeNodeReorderVO conTreeNodeReorderVO);

    /**
     * 获取树节点列表
     * @param conTreeNodeListReqVO 请求参数
     * @return 树结构
     */
    ConTreeNodeListRespVO listNode(ConTreeNodeListReqVO conTreeNodeListReqVO);
}

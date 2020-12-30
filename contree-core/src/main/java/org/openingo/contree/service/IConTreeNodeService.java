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

package org.openingo.contree.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.openingo.contree.base.entity.ConTreeNodeDO;

import java.util.List;

/**
 * <p>
 * 树节点数据 服务类
 * </p>
 *
 * @author Qicz
 * @since 2020-12-18
 */
public interface IConTreeNodeService extends IService<ConTreeNodeDO> {

    /**
     * 获取rootNodeId的子节点，包括自身
     * @param treeCode 树编码
     * @param rootNodeId 父节点
     * @param recursion 是否递归查找
     * @return 节点list
     */
    List<ConTreeNodeDO> listNodes(String treeCode, Integer rootNodeId, boolean recursion);

    /**
     * 获取rootNodeId的子节点
     * @param treeCode 树编码
     * @param rootNodeId 父节点
     * @param recursion 是否递归查找
     * @param includeSelf 是否包括自身
     * @return 节点list
     */
    List<ConTreeNodeDO> listNodes(String treeCode, Integer rootNodeId, boolean recursion, boolean includeSelf);

    /**
     * 获取rootNodeId的子节点
     * @param treeCode 树编码
     * @param rootNodeId 父节点
     * @param nodeName 节点名称
     * @param recursion 是否递归查找
     * @param includeSelf 是否包括自身
     * @return 节点list
     */
    List<ConTreeNodeDO> listNodes(String treeCode, Integer rootNodeId, String nodeName, boolean recursion, boolean includeSelf);
}

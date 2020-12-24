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

package org.openingo.contree.vo.list;

import lombok.Data;
import org.openingo.contree.vo.ConTreeNodeVO;
import org.openingo.jdkits.tree.ITreeNode;
import org.openingo.jdkits.tree.TreeBuilder;
import org.openingo.jdkits.validate.ValidateKit;

import java.util.Comparator;
import java.util.List;

/**
 * ConTreeNodeTreeRespVO
 *
 * @author Qicz
 */
@Data
public class ConTreeNodeTreeRespVO extends ConTreeNodeListRespVO {

    /**
     * 节点数据
     */
    private List<ConTreeNodeTreeItemRespVO> nodes;

    /**
     * 转化为树结构
     */
    public void toTree() {
        if (ValidateKit.isNotEmpty(this.nodes)) {
            this.setNodeCount(this.nodes.size());
            this.nodes = TreeBuilder.buildTree(TreeBuilder.RootNode.ZERO_ID, this.nodes, Comparator.comparingInt(ConTreeNodeVO::getNodeOrder));
        }
    }

    @Data
    static class ConTreeNodeTreeItemRespVO extends ConTreeNodeVO implements ITreeNode<ConTreeNodeTreeItemRespVO> {

        private List<ConTreeNodeTreeItemRespVO> sonNodes;

        public Integer getSonCount() {
            return this.sonNodes.size();
        }

        /**
         * @return current node id
         */
        @Override
        public String nodeId() {
            return this.getNodeId().toString();
        }

        /**
         * @return current node parent id
         */
        @Override
        public String nodeParentId() {
            return this.getRootNodeId().toString();
        }

        /**
         * @param childNodes put the child nodes
         */
        @Override
        public void putChildNodes(List<ConTreeNodeTreeItemRespVO> childNodes) {
            this.sonNodes = childNodes;
        }
    }
}

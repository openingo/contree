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

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.openingo.contree.vo.ConTreeNodeVO;
import org.openingo.jdkits.tree.ITreeNode;
import org.openingo.jdkits.tree.TreeBuilder;
import org.openingo.jdkits.validate.ValidateKit;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

/**
 * ConTreeNodeListRespVO
 *
 * @author Qicz
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ConTreeNodeListRespVO implements Serializable {

    /**
     * 节点数量
     */
    private Integer nodeCount;

    /**
     * 节点数据
     */
    private List<ConTreeNodeRespVO> nodes;

    /**
     * 重新构造数据
     */
    public void redraw(boolean toTree, Integer rootNodeId) {
        int nodeCount = 0;
        if (ValidateKit.isNotEmpty(this.nodes)) {
            nodeCount = this.nodes.size();
            if (toTree) {
                Integer zero = 0;
                if (zero.equals(rootNodeId)) {
                    this.nodes = TreeBuilder.buildTree(TreeBuilder.RootNode.ZERO_ID, this.nodes, Comparator.comparingInt(ConTreeNodeVO::getNodeOrder));
                } else {
                    this.nodes = TreeBuilder.buildTree(rootNodeId.toString(), this.nodes, Comparator.comparingInt(ConTreeNodeVO::getNodeOrder));
                }
            }
        }
        this.setNodeCount(nodeCount);
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class ConTreeNodeRespVO extends ConTreeNodeVO implements ITreeNode<ConTreeNodeRespVO> {

        /**
         * 子节点数量
         */
        private Integer sonCount;

        /**
         * 子节点
         */
        private List<ConTreeNodeRespVO> sonNodes;

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
        public void putChildNodes(List<ConTreeNodeRespVO> childNodes) {
            this.sonNodes = childNodes;
            this.sonCount = childNodes.size();
        }
    }
}

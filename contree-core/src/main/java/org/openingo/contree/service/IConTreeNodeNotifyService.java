package org.openingo.contree.service;

import org.openingo.contree.entity.ConTreeNode;

import java.util.List;

/**
 * IConTreeNodeNotifyService
 *
 * @author Qicz
 */
public interface IConTreeNodeNotifyService {

    /**
     * 添加节点
     *
     * @param treeCode 树编码
     * @param conTreeNode 添加的节点
     */
    void addNode(String treeCode, ConTreeNode conTreeNode);

    /**
     * 编辑节点
     *
     * @param treeCode 树编码
     * @param conTreeNode 编辑的节点
     */
    void editNode(String treeCode, ConTreeNode conTreeNode);

    /**
     * 删除节点
     *
     * @param treeCode 树编码
     * @param conTreeNodes 删除信息
     */
    void deleteNode(String treeCode, List<ConTreeNode> conTreeNodes);
}

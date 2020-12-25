package org.openingo.contree.service.impl;

import org.openingo.contree.entity.ConTreeNode;
import org.openingo.contree.notify.ConTreeObservable;
import org.openingo.contree.notify.IConTreeObserver;
import org.openingo.contree.service.IConTreeNodeNotifyService;
import org.openingo.jdkits.validate.ValidateKit;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * ConTreeNodeNotifyServiceImpl
 *
 * @author Qicz
 */
@Service
public class ConTreeNodeNotifyServiceImpl implements IConTreeNodeNotifyService, ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;

    /**
     * 事件观察对象
     */
    private ConTreeObservable conTreeObservable = new ConTreeObservable();

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, IConTreeObserver> treeObserverMap = this.applicationContext.getBeansOfType(IConTreeObserver.class);
        if (ValidateKit.isNotNull(treeObserverMap)) {
            treeObserverMap.values().forEach(this.conTreeObservable::addObserver);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 添加节点
     *
     * @param treeCode    树编码
     * @param conTreeNode 添加的节点
     */
    @Override
    public void addNode(String treeCode, ConTreeNode conTreeNode) {
        this.conTreeObservable.notifyCreateActionToObservers(treeCode, conTreeNode);
    }

    /**
     * 编辑节点
     *
     * @param treeCode    树编码
     * @param conTreeNode 编辑的节点
     */
    @Override
    public void editNode(String treeCode, ConTreeNode conTreeNode) {
        this.conTreeObservable.notifyUpdateActionToObservers(treeCode, conTreeNode);
    }

    /**
     * 删除节点
     *
     * @param treeCode     树编码
     * @param conTreeNodes 删除信息
     */
    @Override
    public void deleteNode(String treeCode, List<ConTreeNode> conTreeNodes) {
        this.conTreeObservable.notifyDeleteActionToObservers(treeCode, conTreeNodes);
    }
}

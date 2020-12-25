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

package org.openingo.contree.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.openingo.contree.base.entity.ConTreeNodeDO;
import org.openingo.contree.mapper.ConTreeNodeMapperX;
import org.openingo.contree.service.IConTreeNodeService;
import org.openingo.contree.service.notify.ConTreeObservable;
import org.openingo.contree.service.notify.IConTreeObserver;
import org.openingo.jdkits.collection.ListKit;
import org.openingo.jdkits.validate.ValidateKit;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 树节点数据 服务实现类
 * </p>
 *
 * @author Qicz
 * @since 2020-12-18
 */
@Service
public class ConTreeNodeServiceImpl extends ServiceImpl<ConTreeNodeMapperX, ConTreeNodeDO> implements IConTreeNodeService, ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;

    /**
     * 事件观察对象
     */
    private ConTreeObservable conTreeObservable = new ConTreeObservable();

    /**
     * 判断节点是否合法
     *
     * @param treeCode 树编码
     * @param nodeId   节点id
     * @return true成功false失败
     */
    @Override
    public boolean isValidNode(String treeCode, Integer nodeId) {
        ConTreeNodeDO nodeDO = this.getById(nodeId);
        return ValidateKit.isNotNull(nodeDO) && treeCode.equals(nodeDO.getTreeCode());
    }

    /**
     * 获取rootNodeId的子节点
     *
     * @param treeCode 树编码
     * @param rootNodeId 父节点
     * @param nodeName 节点名称
     * @param recursion  是否递归查找
     * @return 节点list
     */
    @Override
    public List<ConTreeNodeDO> listNodes(String treeCode,
                                         Integer rootNodeId,
                                         String nodeName,
                                         boolean recursion) {
        List<ConTreeNodeDO> allNodes = ConTreeNodeDO.dao(ConTreeNodeDO.class)
                .like(ValidateKit.isNotNull(nodeName), ConTreeNodeDO::getNodeName, nodeName)
                .eq(ConTreeNodeDO::getTreeCode, treeCode)
                .eq(ConTreeNodeDO::getRootNodeId, rootNodeId).doQuery();
        if (!recursion) {
            return allNodes;
        }
        this.recursiveListNodes(allNodes, allNodes.stream().map(ConTreeNodeDO::getNodeId).collect(Collectors.toList()));
        if (ValidateKit.isNull(allNodes)) {
            allNodes = ListKit.emptyList();
        }
        return allNodes;
    }

    /**
     * 递归获取树节点数据
     * @param allNodes 完整数据
     * @param rootNodeIds 父节点ids
     */
    private void recursiveListNodes(List<ConTreeNodeDO> allNodes, List<Integer> rootNodeIds) {
        if (ValidateKit.isNull(rootNodeIds)) {
            return;
        }
        List<ConTreeNodeDO> partNodes = ConTreeNodeDO.dao(ConTreeNodeDO.class).in(ConTreeNodeDO::getRootNodeId, rootNodeIds).doQuery();
        if (ValidateKit.isNull(partNodes)) {
            return;
        }
        allNodes.addAll(partNodes);
        this.recursiveListNodes(allNodes, partNodes.stream().map(ConTreeNodeDO::getNodeId).collect(Collectors.toList()));
    }

    /**
     * Invoked by the containing {@code BeanFactory} after it has set all bean properties
     * and satisfied {@link BeanFactoryAware}, {@code ApplicationContextAware} etc.
     * <p>This method allows the bean instance to perform validation of its overall
     * configuration and final initialization when all bean properties have been set.
     *
     * @throws Exception in the event of misconfiguration (such as failure to set an
     *                   essential property) or if initialization fails for any other reason
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, IConTreeObserver> treeObserverMap = this.applicationContext.getBeansOfType(IConTreeObserver.class);
        if (ValidateKit.isNotNull(treeObserverMap)) {
            treeObserverMap.values().forEach(this.conTreeObservable::addObserver);
        }
    }

    /**
     * Set the ApplicationContext that this object runs in.
     * Normally this call will be used to initialize the object.
     * <p>Invoked after population of normal bean properties but before an init callback such
     * as {@link InitializingBean#afterPropertiesSet()}
     * or a custom init-method. Invoked after {@link ResourceLoaderAware#setResourceLoader},
     * {@link ApplicationEventPublisherAware#setApplicationEventPublisher} and
     * {@link MessageSourceAware}, if applicable.
     *
     * @param applicationContext the ApplicationContext object to be used by this object
     * @throws ApplicationContextException in case of context initialization errors
     * @throws BeansException              if thrown by application context methods
     * @see BeanInitializationException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

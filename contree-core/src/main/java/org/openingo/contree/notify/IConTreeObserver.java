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

package org.openingo.contree.notify;

import org.openingo.contree.entity.ConTreeNode;
import org.openingo.jdkits.validate.ValidateKit;

import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * IConTreeObserver
 *
 * @author Qicz
 */
public interface IConTreeObserver extends Observer {

    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    default void update(Observable o, Object arg) {
        if (o instanceof ConTreeObservable) {
            ConTreeObservable.NotifyData notifyData = (ConTreeObservable.NotifyData)arg;
            if (ValidateKit.isNull(notifyData)) {
                return;
            }
            String treeCode = notifyData.treeCode;
            switch (notifyData.notifyType) {
                case DELETE: {
                    this.onTreeNodeDelete(treeCode, Collections.unmodifiableList((List<ConTreeNode>) notifyData.notifyData));
                }
                    break;
                case UPDATE: {
                    this.onTreeNodeUpdate(treeCode, (ConTreeNode)notifyData.notifyData);
                }
                    break;
                case CREATE: {
                    this.onTreeNodeCreate(treeCode, (ConTreeNode)notifyData.notifyData);
                }
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + notifyData.notifyType);
            }
        }
    }

    /**
     * 新增处理响应
     * <note>
     *     1. 这处理与树创建在同一个事务中；
     *     2. 业务端收到onTreeNodeCreate消息后，处理完成业务逻辑；
     *     3. 以上都无异常，则事务正常提交。
     * </note>
     * @param treeCode 树编码
     * @param node 新增node信息
     */
    default void onTreeNodeCreate(String treeCode, ConTreeNode node) {

    }

    /**
     * 删除处理响应
     * <note>
     *     1. 这处理与树删除在同一个事务中；
     *     2. 业务端收到onTreeNodeDelete消息后，处理完成业务逻辑；
     *     3. 以上都无异常，则事务正常提交。
     * </note>
     * @param treeCode 树编码
     * @param nodes 删除的node信息
     */
    default void onTreeNodeDelete(String treeCode, List<ConTreeNode> nodes) {

    }

    /**
     * 更新处理响应
     * <note>
     *     1. 这处理与树更新在同一个事务中；
     *     2. 业务端收到onTreeNodeUpdate消息后，处理完成业务逻辑；
     *     3. 以上都无异常，则事务正常提交。
     * </note>
     * @param treeCode 树编码
     * @param node 更新的node信息
     */
    default void onTreeNodeUpdate(String treeCode, ConTreeNode node) {

    }
}

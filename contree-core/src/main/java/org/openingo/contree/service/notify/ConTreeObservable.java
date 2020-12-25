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

package org.openingo.contree.service.notify;

import lombok.Builder;

import java.util.Observable;
import java.util.Observer;

/**
 * ConTreeObservable
 *
 * @author Qicz
 */
public class ConTreeObservable extends Observable {

    /**
     * 通知类型
     */
    enum NotifyType {
        /**
         * 删除
         */
        DELETE,
        /**
         * 更新
         */
        UPDATE,
        /**
         * 新建
         */
        CREATE;
    }

    /**
     * 通知数据
     */
    @Builder
    static class NotifyData {

        /**
         * 树编码
         */
        String treeCode;

        /**
         * 通知数据
         */
        Object notifyData;

        /**
         * 通知类型
         */
        NotifyType notifyType;
    }


    /**
     * If this object has changed, as indicated by the
     * <code>hasChanged</code> method, then notify all of its observers
     * and then call the <code>clearChanged</code> method to
     * indicate that this object has no longer changed.
     * <p>
     * Each observer has its <code>update</code> method called with two
     * arguments: this observable object and <code>null</code>. In other
     * words, this method is equivalent to:
     * <blockquote><tt>
     * notifyObservers(null)</tt></blockquote>
     *
     * @see Observable#clearChanged()
     * @see Observable#hasChanged()
     * @see Observer#update(Observable, Object)
     */
    @Override
    public void notifyObservers() {
        super.setChanged();
        super.notifyObservers();
    }

    /**
     * If this object has changed, as indicated by the
     * <code>hasChanged</code> method, then notify all of its observers
     * and then call the <code>clearChanged</code> method to indicate
     * that this object has no longer changed.
     * <p>
     * Each observer has its <code>update</code> method called with two
     * arguments: this observable object and the <code>arg</code> argument.
     *
     * @param arg any object.
     * @see Observable#clearChanged()
     * @see Observable#hasChanged()
     * @see Observer#update(Observable, Object)
     */
    @Override
    public void notifyObservers(Object arg) {
        super.setChanged();
        super.notifyObservers(arg);
    }

    /**
     * 通知变动
     * @param notifyType 通知类型
     * @param treeCode 树编码
     * @param notifyData 通知数据
     */
    private void notifyObservers(NotifyType notifyType, String treeCode, Object notifyData) {
        this.notifyObservers(NotifyData.builder().notifyType(notifyType).treeCode(treeCode).notifyData(notifyData).build());
    }

    /**
     * 通知变动(新增）
     * @param treeCode 树编码
     * @param notifyData 通知数据
     */
    public void notifyCreateActionToObservers(String treeCode, Object notifyData) {
        this.notifyObservers(NotifyType.CREATE, treeCode, notifyData);
    }

    /**
     * 通知变动（删除）
     * @param treeCode 树编码
     * @param notifyData 通知数据
     */
    public void notifyDeleteActionToObservers(String treeCode, Object notifyData) {
        this.notifyObservers(NotifyType.DELETE, treeCode, notifyData);
    }

    /**
     * 通知变动(更新）
     * @param treeCode 树编码
     * @param notifyData 通知数据
     */
    public void notifyUpdateActionToObservers(String treeCode, Object notifyData) {
        this.notifyObservers(NotifyType.UPDATE, treeCode, notifyData);
    }
}

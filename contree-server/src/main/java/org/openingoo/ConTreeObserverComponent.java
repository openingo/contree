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

package org.openingoo;

import lombok.extern.slf4j.Slf4j;
import org.openingo.contree.entity.ConTreeNode;
import org.openingo.contree.service.notify.IConTreeObserver;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ConTreeObserverComponent
 *
 * @author Qicz
 */
@Component
@Slf4j
public class ConTreeObserverComponent implements IConTreeObserver {

    /**
     * 删除处理响应
     * <note>
     * 1. 这处理与树删除在同一个事务中；
     * 2. 业务端收到onTreeNodeDelete消息后，处理完成业务逻辑；
     * 3. 以上都无异常，则事务正常提交。
     * </note>
     *
     * @param treeCode 树编码
     * @param nodes    删除的node信息
     */
    @Override
    public void onTreeNodeDelete(String treeCode, List<ConTreeNode> nodes) {
        log.info("😃 treeCode = {}, nodes = {}", treeCode, nodes);
    }
}

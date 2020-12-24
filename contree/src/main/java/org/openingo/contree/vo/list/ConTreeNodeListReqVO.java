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
import org.openingo.contree.vo.base.ConTreeNodeBaseVO;
import org.openingo.jdkits.validate.ValidateKit;

import javax.validation.ValidationException;
import java.util.regex.Pattern;

/**
 * ConTreeNodeListReqVO
 *
 * @author Qicz
 */
@Data
public class ConTreeNodeListReqVO extends ConTreeNodeBaseVO {

    /**
     * 父节点id
     */
    private Integer rootNodeId;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 获取数据类型
     */
    private String fetchType;

    /**
     * 校验数据是否合法
     */
    public void validate() {
        if (ValidateKit.isNull(this.getTreeCode())) {
            throw new ValidationException("树编码不可为空!");
        }
        boolean check = ValidateKit.isNull(this.fetchType)
                || !Pattern.matches("^full$|^ids$|^sons$", this.fetchType);
        if (check) {
            throw new ValidationException("获取类型取值不合法，仅支持\"full\"或\"ids\"或\"sons\"!");
        }
    }
}

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

package org.openingo.contree.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.openingo.contree.vo.base.ConTreeNodeBaseVO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

/**
 * ConTreeNodeVO
 *
 * @author Qicz
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ConTreeNodeVO extends ConTreeNodeBaseVO {

    /**
     * 父节点id
     */
    private Integer rootNodeId;

    /**
     * 节点id
     */
    @NotNull(message = "节点id不能为空!", groups = {VoValidatorGroups.Update.class, VoValidatorGroups.Delete.class})
    private Integer nodeId;

    /**
     * 节点名称
     */
    @NotBlank(message = "名称不可为空!", groups = VoValidatorGroups.Insert.class)
    private String nodeName;

    /**
     * 节点Icon
     */
    private String nodeIcon;

    /**
     * 节点排序
     */
    @NotNull(message = "顺序不能为空!", groups = VoValidatorGroups.Insert.class)
    @PositiveOrZero(message = "只能取0或正整数")
    private Integer nodeOrder;

    /**
     * 节点Tips
     */
    private String nodeTips;

    /**
     * 节点扩展数据
     */
    private Object nodeExtension;

    /**
     * 节点删除模式
     */
    @NotBlank(message = "mode不能为空!", groups = VoValidatorGroups.Delete.class)
    @Pattern(regexp = "^cascade$|^non-sons$", message = "取值不合法，仅支持\"cascade\"或\"non-sons\"!")
    private String mode;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}

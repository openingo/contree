package org.openingo.contree.base.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import org.openingo.mybatisplus.extension.ModelX;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 树节点数据
 * </p>
 *
 * @author Qicz
 * @since 2020-12-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_tree_con_tree_node")
public class ConTreeNodeDO extends ModelX<ConTreeNodeDO> {

    private static final long serialVersionUID=1L;
    

    /**
     * 节点id
     */
    @TableId(value = "node_id", type = IdType.AUTO)
    private Integer nodeId;

    /**
     * 父节点id，默认为0
     */
    private Integer rootNodeId;

    /**
     * 树code
     */
    private String treeCode;

    /**
     * 节点图标uri
     */
    private String nodeIcon;

    /**
     * 节点颜色
     */
    private String nodeColor;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 1可见0不可见
     */
    private Boolean nodeVisible;

    /**
     * 节点顺序
     */
    private Integer nodeOrder;

    /**
     * 节点提示文字
     */
    private String nodeTips;

    /**
     * 扩展信息【json】
     */
    private String nodeExtension;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.nodeId;
    }

}

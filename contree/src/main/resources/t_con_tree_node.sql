SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_con_tree_node
-- ----------------------------
DROP TABLE IF EXISTS `t_con_tree_node`;
CREATE TABLE `t_con_tree_node` (
  `node_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '节点id',
  `root_node_id` int(10) unsigned NOT NULL DEFAULT 0 COMMENT '父节点id，默认为0',
  `tree_code` varchar(100) NOT NULL COMMENT '树code',
  `node_icon` varchar(255) DEFAULT NULL COMMENT '节点图标uri',
  `node_name` varchar(100) NOT NULL COMMENT '节点名称',
  `node_visible` tinyint(1) unsigned NOT NULL DEFAULT 1 COMMENT '1可见0不可见',
  `node_order` int(10) unsigned NOT NULL COMMENT '节点顺序',
  `node_tips` varchar(30) DEFAULT NULL COMMENT '节点提示文字',
  `node_extension` text DEFAULT NULL COMMENT '扩展信息【json】',
  `create_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '更新时间',
  PRIMARY KEY (`node_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='树节点数据';

SET FOREIGN_KEY_CHECKS = 1;

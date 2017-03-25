/*
MySQL Backup
Source Server Version: 5.1.72
Source Database: simple
Date: 2017/1/19 17:02:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
--  Table structure for `cms_article`
-- ----------------------------
DROP TABLE IF EXISTS `cms_article`;
CREATE TABLE `cms_article` (
  `article_id` varchar(32) NOT NULL COMMENT '文章主键',
  `channel_name` varchar(32) DEFAULT NULL COMMENT '频道名称',
  `title` varchar(128) DEFAULT NULL COMMENT '标题',
  `intro` varchar(255) DEFAULT NULL COMMENT '简介',
  `origin` varchar(128) DEFAULT NULL COMMENT '来源',
  `rich_content` text COMMENT '内容',
  `author` varchar(32) DEFAULT NULL COMMENT '作者',
  `pub_date` date DEFAULT NULL COMMENT '发布日期',
  `view_count` int(11) DEFAULT '0' COMMENT '查看次数',
  `disabled` tinyint(1) DEFAULT '0' COMMENT '禁用',
  `owner` varchar(32) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除',
  `cruser` varchar(32) DEFAULT NULL COMMENT '创建者',
  `crtime` datetime DEFAULT NULL COMMENT '创建时间',
  `mduser` varchar(32) DEFAULT NULL COMMENT '修改者',
  `mdtime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章';

-- ----------------------------
--  Table structure for `cms_channel`
-- ----------------------------
DROP TABLE IF EXISTS `cms_channel`;
CREATE TABLE `cms_channel` (
  `channel_name` varchar(32) NOT NULL,
  `parent_name` varchar(32) DEFAULT NULL,
  `viewname` varchar(32) DEFAULT NULL,
  `url` varchar(128) DEFAULT NULL,
  `owner` varchar(32) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  `cruser` varchar(32) DEFAULT NULL,
  `crtime` datetime DEFAULT NULL,
  `mduser` varchar(32) DEFAULT NULL,
  `mdtime` datetime DEFAULT NULL,
  PRIMARY KEY (`channel_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `cms_product`
-- ----------------------------
DROP TABLE IF EXISTS `cms_product`;
CREATE TABLE `cms_product` (
  `product_id` varchar(32) NOT NULL,
  `product_name` varchar(64) DEFAULT NULL,
  `intro` varchar(128) DEFAULT NULL,
  `spec` varchar(64) DEFAULT NULL,
  `rich_content` text,
  `deleted` tinyint(1) DEFAULT NULL,
  `owner` varchar(32) DEFAULT NULL,
  `cruser` varchar(32) DEFAULT NULL,
  `crtime` datetime DEFAULT NULL,
  `mduser` varchar(32) DEFAULT NULL,
  `mdtime` datetime DEFAULT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `cms_product_tag`
-- ----------------------------
DROP TABLE IF EXISTS `cms_product_tag`;
CREATE TABLE `cms_product_tag` (
  `product_id` varchar(32) NOT NULL,
  `tagname` varchar(32) NOT NULL,
  PRIMARY KEY (`product_id`,`tagname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `cms_tag`
-- ----------------------------
DROP TABLE IF EXISTS `cms_tag`;
CREATE TABLE `cms_tag` (
  `tagname` varchar(32) NOT NULL,
  `viewname` varchar(32) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  `owner` varchar(32) DEFAULT NULL,
  `cruser` varchar(32) DEFAULT NULL,
  `crtime` datetime DEFAULT NULL,
  `mduser` varchar(32) DEFAULT NULL,
  `mdtime` datetime DEFAULT NULL,
  PRIMARY KEY (`tagname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comm_attachment`
-- ----------------------------
DROP TABLE IF EXISTS `comm_attachment`;
CREATE TABLE `comm_attachment` (
  `attach_id` varchar(32) NOT NULL,
  `original_attach_id` varchar(32) DEFAULT NULL COMMENT '原始文件编号;被引用文件编号',
  `attach_original_name` varchar(128) DEFAULT NULL COMMENT '原始文件名',
  `attach_path` varchar(128) DEFAULT NULL COMMENT '保存路径',
  `attach_name` varchar(64) DEFAULT NULL COMMENT '文件名',
  `attach_size` int(11) DEFAULT NULL COMMENT '文件大小',
  `attach_type` varchar(32) DEFAULT NULL COMMENT '文件类型',
  `attach_desc` varchar(64) DEFAULT NULL COMMENT '文件说明',
  `attach_sha1` varchar(40) DEFAULT NULL COMMENT '文件SHA1摘要值',
  `image_width` int(11) DEFAULT NULL COMMENT '图片宽度',
  `image_height` int(11) DEFAULT NULL COMMENT '图片高度',
  `deleted` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标志',
  `cruser` varchar(32) DEFAULT NULL COMMENT '创建者',
  `crtime` datetime DEFAULT NULL COMMENT '创建时间',
  `mduser` varchar(32) DEFAULT NULL COMMENT '修改者',
  `mdtime` datetime DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`attach_id`),
  KEY `attach_original_name` (`attach_original_name`),
  KEY `attach_sha1` (`attach_sha1`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comm_attachment_ref`
-- ----------------------------
DROP TABLE IF EXISTS `comm_attachment_ref`;
CREATE TABLE `comm_attachment_ref` (
  `ref_id` varchar(32) NOT NULL,
  `attach_id` varchar(32) DEFAULT NULL,
  `module` varchar(32) DEFAULT NULL,
  `entity` varchar(32) DEFAULT NULL,
  `attr` varchar(32) DEFAULT NULL,
  `owner` varchar(32) DEFAULT NULL,
  `srt` int(11) DEFAULT NULL,
  PRIMARY KEY (`ref_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `sys_group`
-- ----------------------------
DROP TABLE IF EXISTS `sys_group`;
CREATE TABLE `sys_group` (
  `groupname` varchar(32) NOT NULL,
  `viewname` varchar(32) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`groupname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `sys_group_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_group_role`;
CREATE TABLE `sys_group_role` (
  `groupname` varchar(32) NOT NULL,
  `rolename` varchar(32) NOT NULL,
  PRIMARY KEY (`groupname`,`rolename`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menuname` varchar(64) NOT NULL,
  `menu_url` varchar(64) NOT NULL,
  `viewname` varchar(32) DEFAULT NULL,
  `grp` int(11) DEFAULT NULL,
  `srt` int(11) DEFAULT '1',
  `deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`menuname`),
  UNIQUE KEY `menu_url` (`menu_url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `sys_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `permname` varchar(64) NOT NULL,
  `viewname` varchar(32) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`permname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `rolename` varchar(32) NOT NULL,
  `viewname` varchar(32) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`rolename`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `sys_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `rolename` varchar(32) NOT NULL,
  `permname` varchar(64) NOT NULL,
  PRIMARY KEY (`rolename`,`permname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `username` varchar(32) NOT NULL,
  `viewname` varchar(32) DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  `groupname` varchar(32) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `sys_user_group`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_group`;
CREATE TABLE `sys_user_group` (
  `username` varchar(32) NOT NULL,
  `groupname` varchar(32) NOT NULL,
  PRIMARY KEY (`username`,`groupname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `username` varchar(32) NOT NULL,
  `rolename` varchar(32) NOT NULL,
  PRIMARY KEY (`username`,`rolename`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records 
-- ----------------------------
INSERT INTO `cms_article` VALUES ('c31594e575094324ba8ee228040521bc','18','文章标题','文章简介11','文章来源11','<p><span style=\"color: #333333; font-family: \'Helvetica Neue\', Helvetica, Arial, sans-serif; font-weight: bold;\">文章内容11</span></p>','文章作者11','2015-01-11','0','0','jsjrdt','0','jsjrdt','2017-01-19 15:34:34','jsjrdt','2017-01-19 16:16:09');
INSERT INTO `cms_channel` VALUES ('1',NULL,'信用动态',NULL,'jsjrdt','0',NULL,NULL,NULL,NULL), ('16','1','本地动态',NULL,'jsjrdt','0',NULL,NULL,NULL,NULL), ('17','1','省市动态',NULL,'jsjrdt','0',NULL,NULL,NULL,NULL), ('18','1','国内动态',NULL,'jsjrdt','0',NULL,NULL,NULL,NULL), ('19','0','通知公告',NULL,'jsjrdt','0',NULL,NULL,NULL,NULL), ('2',NULL,'信用组织',NULL,'jsjrdt','0',NULL,NULL,NULL,NULL), ('20','6','信用红榜',NULL,'jsjrdt','0',NULL,NULL,NULL,NULL), ('21','6','信用黑榜',NULL,'jsjrdt','0',NULL,NULL,NULL,NULL), ('25','5','企业查询',NULL,'jsjrdt','0',NULL,NULL,NULL,NULL), ('26','5','个人查询',NULL,'jsjrdt','0',NULL,NULL,NULL,NULL), ('27','5','信用审查预约',NULL,'jsjrdt','0',NULL,NULL,NULL,NULL), ('28','2','组织机构',NULL,'jsjrdt','0',NULL,NULL,NULL,NULL), ('29','2','成员单位',NULL,'jsjrdt','0',NULL,NULL,NULL,NULL), ('3',NULL,'信用评级',NULL,'jsjrdt','0',NULL,NULL,NULL,NULL), ('30','2','联系我们',NULL,'jsjrdt','0',NULL,NULL,NULL,NULL), ('31','3','省市级重合同守信用企业查询',NULL,'jsjrdt','0',NULL,NULL,NULL,NULL), ('32','3','质量信用企业查询',NULL,'jsjrdt','0',NULL,NULL,NULL,NULL), ('33','3','高新技术企业查询',NULL,'jsjrdt','0',NULL,NULL,NULL,NULL), ('34','3','食品药品企业信用查询',NULL,'jsjrdt','0',NULL,NULL,NULL,NULL), ('35','3','工程建设领域信用查询',NULL,'jsjrdt','0',NULL,NULL,NULL,NULL), ('36','3','企业纳税信用查询',NULL,'jsjrdt','0',NULL,NULL,NULL,NULL), ('37','3','企业环保信用查询',NULL,'jsjrdt','0',NULL,NULL,NULL,NULL), ('38','3','重点从业人员信用查询',NULL,'jsjrdt','0',NULL,NULL,NULL,NULL), ('4',NULL,'政策法规',NULL,'jsjrdt','0',NULL,NULL,NULL,NULL), ('5',NULL,'信用查询',NULL,'jsjrdt','0',NULL,NULL,NULL,NULL), ('6',NULL,'信用红黑榜',NULL,'jsjrdt','0',NULL,NULL,NULL,NULL), ('7',NULL,'信用知识',NULL,'jsjrdt','0',NULL,NULL,NULL,NULL), ('A',NULL,'A','A','jsjrdt','1','jsjrdt','2017-01-19 16:20:03','jsjrdt','2017-01-19 16:25:44'), ('B',NULL,'B','B','jsjrdt','1','jsjrdt','2017-01-19 16:25:56','jsjrdt','2017-01-19 16:28:24'), ('C',NULL,'CC','jsjrdt','jsjrdt','1','jsjrdt','2017-01-19 16:28:10','jsjrdt','2017-01-19 16:28:21');
INSERT INTO `cms_product` VALUES ('c2ef5fe5b1c141d8bf37ff441b52e0c1','产品名称1','产品简介1','规格型号1','<p><span style=\"color: #333333; font-family: \'Helvetica Neue\', Helvetica, Arial, sans-serif; font-weight: bold;\">产品说明</span></p>','0','jsjrdt','jsjrdt','2017-01-19 11:44:47',NULL,NULL), ('f08b0474624a48dda51e7e5bd58b038e','C','C','C','<p>C</p>','0','jsjrdt','jsjrdt','2017-01-19 16:28:46',NULL,NULL), ('f2eb8ab2d7874bb7a12ccbe25ae0ab58','产品名称','产品简介','规格型号','<p><span style=\"color: #333333; font-family: \'Helvetica Neue\', Helvetica, Arial, sans-serif; font-weight: bold;\">产品说明</span></p>','0','jsjrdt','jsjrdt','2017-01-19 09:43:01','jsjrdt','2017-01-19 11:11:02');
INSERT INTO `cms_product_tag` VALUES ('c2ef5fe5b1c141d8bf37ff441b52e0c1','T1000'), ('f08b0474624a48dda51e7e5bd58b038e','T1000'), ('f2eb8ab2d7874bb7a12ccbe25ae0ab58','T1000'), ('f2eb8ab2d7874bb7a12ccbe25ae0ab58','T1010');
INSERT INTO `cms_tag` VALUES ('T1000','12KV触头盒系列','0','jsjrdt','admin','2017-01-16 10:03:59',NULL,NULL), ('T1010','12KV管套系列','0','jsjrdt','admin','2017-01-16 10:03:59',NULL,NULL), ('T1020','12KV绝缘子系列','0','jsjrdt','admin','2017-01-16 10:03:59',NULL,NULL), ('T1030','支杆/拉杆系列','0','jsjrdt','admin','2017-01-16 10:03:59',NULL,NULL), ('T1040','PT车系列/上下绝缘罩/触臂套管','0','jsjrdt','admin','2017-01-16 10:03:59',NULL,NULL), ('T1050','环氧浇注变压器专用绝缘子、垫块','0','jsjrdt','admin','2017-01-16 10:03:59',NULL,NULL), ('T1060','20-40.5KV触头盒套管系列','0','jsjrdt','admin','2017-01-16 10:03:59',NULL,NULL), ('T1070','20-40.5KV绝缘子系列','0','jsjrdt','admin','2017-01-16 10:03:59',NULL,NULL), ('T1080','12-40.5KV传感器/套管系列','0','jsjrdt','admin','2017-01-16 10:03:59',NULL,NULL), ('T1090','静触头','0','jsjrdt','admin','2017-01-16 10:03:59',NULL,NULL), ('T1100','显示器系列','0','jsjrdt','admin','2017-01-16 10:03:59',NULL,NULL), ('T1110','中置柜接地开关JN15-12/31.5','0','jsjrdt','admin','2017-01-16 10:03:59',NULL,NULL), ('T1120','DSN-III系列户内电磁锁','0','jsjrdt','admin','2017-01-16 10:03:59',NULL,NULL), ('T1130','JSN(W)I型防误操作程序锁','0','jsjrdt','admin','2017-01-16 10:03:59',NULL,NULL), ('T1140','避雷器系列','0','jsjrdt','admin','2017-01-16 10:03:59',NULL,NULL), ('T1150','机械闭锁','0','jsjrdt','admin','2017-01-16 10:03:59',NULL,NULL);
INSERT INTO `sys_group` VALUES ('GRP_SITE_DT','大唐电气','0'), ('GRP_SYS_ADMIN','系统管理员组','0');
INSERT INTO `sys_menu` VALUES ('admin:dashboard','/admin/dashboard','仪表盘',NULL,'10','0'), ('admin:group','/admin/group','群组管理',NULL,'30','0'), ('admin:permission','/admin/permission','权限管理',NULL,'50','0'), ('admin:role','/admin/role','角色管理',NULL,'40','0'), ('admin:user','/admin/user','用户管理',NULL,'20','0'), ('cms:article','/cms/article','文章管理',NULL,'90','0'), ('cms:channel','/cms/channel','频道管理',NULL,'80','0'), ('cms:product','/cms/product','产品管理',NULL,'70','0'), ('cms:tag','/cms/tag','标签管理',NULL,'60','0');
INSERT INTO `sys_role` VALUES ('ROLE_CMS_ADMIN','站点管理员','0'), ('ROLE_SYS_ADMIN','系统管理员','0');
INSERT INTO `sys_user` VALUES ('admin','系统管理员','0f6b6faebd3bed09de0ef528d53623013d8c4e7a25370379162b9f9f6bbb4bcd1bd017b3cb3ade8a06aa0afa5cbcb0880aec1368236b252a846c15f9eca36da1','GRP_SYS_ADMIN','0'), ('jsjrdt','江苏句容大唐电气','5e7793ce04ca70c7b7b805c6236bbd572e07036621973eae1f2a182ab97b9bae085af73820607a0e065e95e9cb0d1ab90db218fdaa0c146a4e0cc07236c3b9d4','GRP_SITE_DT','0');
INSERT INTO `sys_user_role` VALUES ('admin','ROLE_CMS_ADMIN'), ('admin','ROLE_SYS_ADMIN'), ('jsjrdt','ROLE_CMS_ADMIN');

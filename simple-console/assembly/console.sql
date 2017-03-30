/*
 Navicat Premium Backup

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50550
 Source Host           : localhost
 Source Database       : sys

 Target Server Type    : MySQL
 Target Server Version : 50550
 File Encoding         : utf-8

 Date: 03/30/2017 22:27:15 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `sys_conf`
-- ----------------------------
DROP TABLE IF EXISTS `sys_conf`;
CREATE TABLE `sys_conf` (
  `conf_code` varchar(32) NOT NULL COMMENT '配置代码',
  `conf_name` varchar(32) DEFAULT NULL COMMENT '配置名称',
  `conf_value` varchar(500) DEFAULT NULL COMMENT '配置值',
  `deleted` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标志',
  `cruser` varchar(32) DEFAULT NULL COMMENT '创建者',
  `crtime` datetime DEFAULT NULL COMMENT '创建时间',
  `mduser` varchar(32) DEFAULT NULL COMMENT '修改者',
  `mdtime` datetime DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`conf_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='配置';

-- ----------------------------
--  Table structure for `sys_dict`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `dict_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '字典编号',
  `parent_id` int(11) unsigned DEFAULT NULL COMMENT '父级字典编号',
  `dict_name` varchar(64) DEFAULT NULL COMMENT '字典名称｜字典项名称',
  `dict_value` varchar(64) DEFAULT NULL COMMENT '字典代码｜字典项值',
  `srt` int(11) unsigned DEFAULT NULL COMMENT '排序',
  `disabled` tinyint(1) unsigned DEFAULT NULL COMMENT '禁用',
  `deleted` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标志',
  `cruser` varchar(32) DEFAULT NULL COMMENT '创建者',
  `crtime` datetime DEFAULT NULL COMMENT '创建时间',
  `mduser` varchar(32) DEFAULT NULL COMMENT '修改者',
  `mdtime` datetime DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`dict_id`),
  UNIQUE KEY `UKA72BFD3A63DB4E048945FC882FD8EAEA` (`parent_id`,`dict_value`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典';

-- ----------------------------
--  Table structure for `sys_group`
-- ----------------------------
DROP TABLE IF EXISTS `sys_group`;
CREATE TABLE `sys_group` (
  `groupname` varchar(32) NOT NULL COMMENT '群组名称',
  `viewname` varchar(32) DEFAULT NULL COMMENT '显示名称',
  `deleted` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标志',
  `cruser` varchar(32) DEFAULT NULL COMMENT '创建者',
  `crtime` datetime DEFAULT NULL COMMENT '创建时间',
  `mduser` varchar(32) DEFAULT NULL COMMENT '修改者',
  `mdtime` datetime DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`groupname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='群组';

-- ----------------------------
--  Table structure for `sys_group_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_group_role`;
CREATE TABLE `sys_group_role` (
  `groupname` varchar(32) NOT NULL COMMENT '群组名称',
  `rolename` varchar(32) NOT NULL COMMENT '角色名称',
  `deleted` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标志',
  `cruser` varchar(32) DEFAULT NULL COMMENT '创建者',
  `crtime` datetime DEFAULT NULL COMMENT '创建时间',
  `mduser` varchar(32) DEFAULT NULL COMMENT '修改者',
  `mdtime` datetime DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`groupname`,`rolename`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='群组角色关系';

-- ----------------------------
--  Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menuname` varchar(64) NOT NULL COMMENT '菜单名称',
  `parent_name` varchar(64) DEFAULT NULL COMMENT '父级菜单名称',
  `menu_url` varchar(128) DEFAULT NULL COMMENT '菜单链接',
  `viewname` varchar(32) DEFAULT NULL COMMENT '显示名称',
  `srt` int(11) unsigned DEFAULT '1' COMMENT '排序',
  `deleted` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标志',
  `cruser` varchar(32) DEFAULT NULL COMMENT '创建者',
  `crtime` datetime DEFAULT NULL COMMENT '创建时间',
  `mduser` varchar(32) DEFAULT NULL COMMENT '修改者',
  `mdtime` datetime DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`menuname`),
  UNIQUE KEY `menu_url` (`menu_url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单';

-- ----------------------------
--  Table structure for `sys_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `permname` varchar(64) NOT NULL COMMENT '权限名称',
  `parent_name` varchar(64) DEFAULT NULL COMMENT '父级权限名称',
  `viewname` varchar(32) DEFAULT NULL COMMENT '显示名称',
  `deleted` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标志',
  `cruser` varchar(32) DEFAULT NULL COMMENT '创建者',
  `crtime` datetime DEFAULT NULL COMMENT '创建时间',
  `mduser` varchar(32) DEFAULT NULL COMMENT '修改者',
  `mdtime` datetime DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`permname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限';

-- ----------------------------
--  Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `rolename` varchar(32) NOT NULL COMMENT '角色名称',
  `viewname` varchar(32) DEFAULT NULL COMMENT '显示名称',
  `deleted` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标志',
  `cruser` varchar(32) DEFAULT NULL COMMENT '创建者',
  `crtime` datetime DEFAULT NULL COMMENT '创建时间',
  `mduser` varchar(32) DEFAULT NULL COMMENT '修改者',
  `mdtime` datetime DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`rolename`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
--  Table structure for `sys_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `rolename` varchar(32) NOT NULL COMMENT '角色名称',
  `menuname` varchar(64) NOT NULL COMMENT '菜单名称',
  `deleted` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标志',
  `cruser` varchar(32) DEFAULT NULL COMMENT '创建者',
  `crtime` datetime DEFAULT NULL COMMENT '创建时间',
  `mduser` varchar(32) DEFAULT NULL COMMENT '修改者',
  `mdtime` datetime DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`rolename`,`menuname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色菜单关系';

-- ----------------------------
--  Table structure for `sys_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `rolename` varchar(32) NOT NULL COMMENT '角色名称',
  `permname` varchar(64) NOT NULL COMMENT '权限名称',
  `deleted` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标志',
  `cruser` varchar(32) DEFAULT NULL COMMENT '创建者',
  `crtime` datetime DEFAULT NULL COMMENT '创建时间',
  `mduser` varchar(32) DEFAULT NULL COMMENT '修改者',
  `mdtime` datetime DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`rolename`,`permname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限关系';

-- ----------------------------
--  Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `username` varchar(32) NOT NULL COMMENT '用户名称',
  `viewname` varchar(32) DEFAULT NULL COMMENT '显示名称',
  `password` varchar(128) DEFAULT NULL COMMENT '密码（hmac sha512）',
  `groupname` varchar(32) DEFAULT NULL COMMENT '群组名称',
  `deleted` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标志',
  `cruser` varchar(32) DEFAULT NULL COMMENT '创建者',
  `crtime` datetime DEFAULT NULL COMMENT '创建时间',
  `mduser` varchar(32) DEFAULT NULL COMMENT '修改者',
  `mdtime` datetime DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户';

-- ----------------------------
--  Table structure for `sys_user_group`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_group`;
CREATE TABLE `sys_user_group` (
  `username` varchar(32) NOT NULL COMMENT '用户名称',
  `groupname` varchar(32) NOT NULL COMMENT '群组名称',
  `deleted` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标志',
  `cruser` varchar(32) DEFAULT NULL COMMENT '创建者',
  `crtime` datetime DEFAULT NULL COMMENT '创建时间',
  `mduser` varchar(32) DEFAULT NULL COMMENT '修改者',
  `mdtime` datetime DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`username`,`groupname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户群组关系';

-- ----------------------------
--  Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `username` varchar(32) NOT NULL COMMENT '用户名称',
  `rolename` varchar(32) NOT NULL COMMENT '角色名称',
  `deleted` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标志',
  `cruser` varchar(32) DEFAULT NULL COMMENT '创建者',
  `crtime` datetime DEFAULT NULL COMMENT '创建时间',
  `mduser` varchar(32) DEFAULT NULL COMMENT '修改者',
  `mdtime` datetime DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`username`,`rolename`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关系';

SET FOREIGN_KEY_CHECKS = 1;

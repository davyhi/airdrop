/*
 Navicat Premium Data Transfer

 Source Server         : localhost_zonghui
 Source Server Type    : MySQL
 Source Server Version : 50716
 Source Host           : localhost:3306
 Source Schema         : airdrop_db

 Target Server Type    : MySQL
 Target Server Version : 50716
 File Encoding         : 65001

 Date: 11/09/2018 14:20:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_log
-- ----------------------------
DROP TABLE IF EXISTS `t_log`;
CREATE TABLE `t_log`  (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作内容',
  `user_id` int(11) DEFAULT NULL COMMENT '操作人',
  `type` int(255) DEFAULT NULL COMMENT '操作类型(0:正常,1:警告,2:错误)',
  `create_stamp` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_money_history
-- ----------------------------
DROP TABLE IF EXISTS `t_money_history`;
CREATE TABLE `t_money_history`  (
  `id` int(11) NOT NULL COMMENT '编号',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '内容',
  `money` int(255) DEFAULT 0 COMMENT '余额',
  `plus_or_minus` int(1) DEFAULT 0 COMMENT '0：加，1：减',
  `create_stamp` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `user_id` int(11) DEFAULT NULL COMMENT '用户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_privileges
-- ----------------------------
DROP TABLE IF EXISTS `t_privileges`;
CREATE TABLE `t_privileges`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '权限名称',
  `describe` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '访问地址',
  `type` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '请求类型',
  `code` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '权限code',
  `pid` int(20) DEFAULT NULL COMMENT '父权限id',
  `ppid` int(20) DEFAULT NULL COMMENT '权限归属id',
  `status` int(11) DEFAULT 0 COMMENT '状态 0：正常 1：已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_privileges
-- ----------------------------
INSERT INTO `t_privileges` VALUES (1, '系统权限', '', '', '', '', 0, 0, 0);
INSERT INTO `t_privileges` VALUES (2, '业务权限', '', '', '', '', 0, 0, 0);
INSERT INTO `t_privileges` VALUES (9, '用户管理', '', '', '', '20001', 1, 1, 0);
INSERT INTO `t_privileges` VALUES (10, '用户新增', '', '/user', 'POST', '20002', 9, 1, 0);
INSERT INTO `t_privileges` VALUES (11, '用户删除', '', '/user/{id}', 'DELETE', '20003', 9, 1, 0);
INSERT INTO `t_privileges` VALUES (12, '用户查询', '', '/user', 'GET', '20004', 9, 1, 0);
INSERT INTO `t_privileges` VALUES (13, '用户修改', '', '/user/{id}', 'PUT', '20005', 9, 1, 0);
INSERT INTO `t_privileges` VALUES (14, '角色管理', '', '', '', '30001', 1, 1, 0);
INSERT INTO `t_privileges` VALUES (15, '角色查询', '', '/role', 'GET', '30002', 14, 1, 0);
INSERT INTO `t_privileges` VALUES (16, '角色删除', '', '/role/{id}', 'DELETE', '30003', 14, 1, 0);
INSERT INTO `t_privileges` VALUES (17, '角色修改', '', '/role/{id}', 'PUT', '30004', 14, 1, 0);
INSERT INTO `t_privileges` VALUES (18, '角色新增', '', '/role', 'POST', '30005', 14, 1, 0);
INSERT INTO `t_privileges` VALUES (19, '资源管理', '', '', '', '40001', 1, 1, 0);
INSERT INTO `t_privileges` VALUES (20, '资源新增', '', '/privileges', 'POST', '40002', 19, 1, 0);
INSERT INTO `t_privileges` VALUES (21, '资源修改', '', '/privileges/{id}', 'PUT', '40003', 19, 1, 0);
INSERT INTO `t_privileges` VALUES (22, '资源删除', '', '/privileges/{id}', 'DELETE', '40004', 19, 1, 0);
INSERT INTO `t_privileges` VALUES (23, '资源查询', '', '/privileges', 'GET', '40005', 19, 1, 0);
INSERT INTO `t_privileges` VALUES (24, '校验用户名是否已存在', '', '/user/checkUname', 'POST', '20006', 9, 1, 0);
INSERT INTO `t_privileges` VALUES (25, '用户授权', '', '/user/userAuthorization', 'POST', '20007', 9, 1, 0);
INSERT INTO `t_privileges` VALUES (26, '角色授权', '', '/role/roleAuthorization', 'POST', '30006', 14, 1, 0);
INSERT INTO `t_privileges` VALUES (27, '资源树状列表数据', '', '/privileges/tree', 'GET', '40006', 19, 1, 0);

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色code',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色名称',
  `describe` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色描述',
  `status` int(11) DEFAULT 0 COMMENT '状态 0：正常 1：已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES (1, 'ROLE_ADMIN', '系统管理员', '维护系统所有功能', 0);

-- ----------------------------
-- Table structure for t_role_privileges
-- ----------------------------
DROP TABLE IF EXISTS `t_role_privileges`;
CREATE TABLE `t_role_privileges`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `r_id` int(20) DEFAULT NULL COMMENT '角色id',
  `p_id` int(20) DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role_privileges
-- ----------------------------
INSERT INTO `t_role_privileges` VALUES (1, 1, 9);
INSERT INTO `t_role_privileges` VALUES (2, 1, 10);
INSERT INTO `t_role_privileges` VALUES (3, 1, 11);
INSERT INTO `t_role_privileges` VALUES (4, 1, 12);
INSERT INTO `t_role_privileges` VALUES (5, 1, 13);
INSERT INTO `t_role_privileges` VALUES (6, 1, 14);
INSERT INTO `t_role_privileges` VALUES (7, 1, 15);
INSERT INTO `t_role_privileges` VALUES (8, 1, 16);
INSERT INTO `t_role_privileges` VALUES (9, 1, 17);
INSERT INTO `t_role_privileges` VALUES (10, 1, 18);
INSERT INTO `t_role_privileges` VALUES (11, 1, 19);
INSERT INTO `t_role_privileges` VALUES (12, 1, 20);
INSERT INTO `t_role_privileges` VALUES (13, 1, 21);
INSERT INTO `t_role_privileges` VALUES (14, 1, 22);
INSERT INTO `t_role_privileges` VALUES (15, 1, 23);
INSERT INTO `t_role_privileges` VALUES (16, 1, 24);
INSERT INTO `t_role_privileges` VALUES (17, 1, 25);
INSERT INTO `t_role_privileges` VALUES (18, 1, 26);
INSERT INTO `t_role_privileges` VALUES (19, 1, 27);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号',
  `password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `email` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '地址',
  `company_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '公司名称',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '姓名',
  `money` int(255) DEFAULT NULL COMMENT '账户余额',
  `status` int(1) DEFAULT 0 COMMENT '0：有效，1：无效',
  `enable` bit(1) DEFAULT b'0' COMMENT '0:false 正常，1：true禁用',
  `create_stamp` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, '15211669315', '$2a$10$ZaVaTEpMU4pdsZ/DDHycJermHZzI39Ut0xZ7Tvt3HrnehCoTsBfV2', '15211669315@sina.cn', '星系-地球', '魔橙', '叶生光', 10000, 0, b'0', '2018-09-11 11:38:38');

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `u_id` int(20) NOT NULL COMMENT '用户id',
  `r_id` int(20) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES (1, 1, 1);

SET FOREIGN_KEY_CHECKS = 1;

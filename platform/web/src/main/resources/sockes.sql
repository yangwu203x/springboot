/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : sockes

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-01-31 18:47:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_account`
-- ----------------------------
DROP TABLE IF EXISTS `sys_account`;
CREATE TABLE `sys_account` (
  `uid` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `state` tinyint(4) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `UK_hsqa0fp3f7rv9yj885sixijg8` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_account
-- ----------------------------
INSERT INTO `sys_account` VALUES ('1', '系统管理员', 'd3c59d25033dbf980d29554025c23a75', '8d78869f470951332959580424d4bf4f', '0', 'admin');
INSERT INTO `sys_account` VALUES ('2', '2', '2', '2', '2', '2');
INSERT INTO `sys_account` VALUES ('3', '3', '3', '3', '3', '3');
INSERT INTO `sys_account` VALUES ('4', '4', '4', '4', '4', '4');
INSERT INTO `sys_account` VALUES ('5', '5', '4', '4', '4', '5');
INSERT INTO `sys_account` VALUES ('6', '6', '6', '6', '6', '6');
INSERT INTO `sys_account` VALUES ('7', '7', '7', '7', '7', '7');
INSERT INTO `sys_account` VALUES ('8', '8', '8', '8', '8', '8');
INSERT INTO `sys_account` VALUES ('9', '9', '9', '9', '9', '9');
INSERT INTO `sys_account` VALUES ('10', '10', '10', '10', '10', '10');
INSERT INTO `sys_account` VALUES ('11', '11', '11', '11', '11', '11');
INSERT INTO `sys_account` VALUES ('12', '12', '12', '12', '12', '12');
INSERT INTO `sys_account` VALUES ('13', '13', '13', '13', '13', '13');

-- ----------------------------
-- Table structure for `sys_account_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_account_role`;
CREATE TABLE `sys_account_role` (
  `role_id` bigint(20) NOT NULL,
  `uid` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`uid`),
  KEY `FKkfnecgmvuv3qkmucux9uk7vuo` (`uid`),
  CONSTRAINT `FK1w5h6tcydfsigq007wlkgiy1n` FOREIGN KEY (`uid`) REFERENCES `sys_account` (`uid`),
  CONSTRAINT `FK839tx3fe4hneuji01cb9blorx` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`),
  CONSTRAINT `FKhh52n8vd4ny9ff4x9fb8v65qx` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`),
  CONSTRAINT `FKkfnecgmvuv3qkmucux9uk7vuo` FOREIGN KEY (`uid`) REFERENCES `sys_account` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_account_role
-- ----------------------------
INSERT INTO `sys_account_role` VALUES ('1', '1');

-- ----------------------------
-- Table structure for `sys_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `available` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `parent_ids` varchar(255) DEFAULT NULL,
  `permission` varchar(255) DEFAULT NULL,
  `resource_type` enum('menu','button') DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1', null, '系统管理', '0', '0', '', 'menu', null, 'Hui-iconfont Hui-iconfont-manage2');
INSERT INTO `sys_permission` VALUES ('2', null, '管理员列表', '1', '1', 'role:create,role:update,role:delete,role:view', 'menu', '/account/listAccount', '&#;');
INSERT INTO `sys_permission` VALUES ('3', null, '权限列表', '1', '1', 'role:create,role:update,role:delete,role:view', 'menu', '/permission/listPermission', '&#;');
INSERT INTO `sys_permission` VALUES ('4', null, '角色列表', '1', '1', 'role:create,role:update,role:delete,role:view', 'menu', '/role/listRole', '&#;');
INSERT INTO `sys_permission` VALUES ('5', null, '歌曲管理', '0', '0', null, 'menu', null, 'Hui-iconfont Hui-iconfont-manage2');
INSERT INTO `sys_permission` VALUES ('6', null, '歌曲列表', '5', '5', 'role:create,role:update,role:delete,role:view', 'menu', '/song/listSong', '&#;');
INSERT INTO `sys_permission` VALUES ('7', null, '歌星管理', '5', '5', 'role:create,role:update,role:delete,role:view', 'menu', '/singer/listSinger', null);
INSERT INTO `sys_permission` VALUES ('8', null, '语种列表', '1', '1', 'role:create,role:update,role:delete,role:view', 'menu', '/lang/listLang', null);
INSERT INTO `sys_permission` VALUES ('9', null, '歌手类型列表', '1', '1', 'role:create,role:update,role:delete,role:view', 'menu', '/singerType/listSingerType', null);
INSERT INTO `sys_permission` VALUES ('10', null, '热门歌曲列表', '5', '5', 'role:create,role:update,role:delete,role:view', 'menu', '/hotSong/listHostSong', null);
INSERT INTO `sys_permission` VALUES ('11', null, '新歌推荐列表', '5', '5', 'role:create,role:update,role:delete,role:view', 'menu', '/recSong/listRecSong', null);

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `available` bit(1) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '', '系统管理员', 'admin');

-- ----------------------------
-- Table structure for `sys_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `role_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`permission_id`),
  KEY `FKomxrs8a388bknvhjokh440waq` (`permission_id`),
  CONSTRAINT `FK9q28ewrhntqeipl1t04kh1be7` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`),
  CONSTRAINT `FKomxrs8a388bknvhjokh440waq` FOREIGN KEY (`permission_id`) REFERENCES `sys_permission` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES ('1', '1');
INSERT INTO `sys_role_permission` VALUES ('1', '2');
INSERT INTO `sys_role_permission` VALUES ('1', '3');

-- ----------------------------
-- Table structure for `tb_apk`
-- ----------------------------
DROP TABLE IF EXISTS `tb_apk`;
CREATE TABLE `tb_apk` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `apk_down_path` varchar(255) DEFAULT NULL,
  `apk_name` varchar(255) DEFAULT NULL,
  `apk_path` varchar(255) DEFAULT NULL,
  `apk_version` varchar(255) DEFAULT NULL,
  `server_url` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_apk
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_hot_song`
-- ----------------------------
DROP TABLE IF EXISTS `tb_hot_song`;
CREATE TABLE `tb_hot_song` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `song_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_hot_song
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_lang`
-- ----------------------------
DROP TABLE IF EXISTS `tb_lang`;
CREATE TABLE `tb_lang` (
  `lang_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lang_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`lang_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_lang
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_new_song`
-- ----------------------------
DROP TABLE IF EXISTS `tb_new_song`;
CREATE TABLE `tb_new_song` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `song_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_pj725q1fv346mcsdwbmid4i6m` (`song_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_new_song
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_rec_song`
-- ----------------------------
DROP TABLE IF EXISTS `tb_rec_song`;
CREATE TABLE `tb_rec_song` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `song_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_axv33x2erjr4myw1vpd0wnp1g` (`song_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_rec_song
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_singer`
-- ----------------------------
DROP TABLE IF EXISTS `tb_singer`;
CREATE TABLE `tb_singer` (
  `singer_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sing_name` varchar(255) DEFAULT NULL,
  `singer_spell` varchar(255) DEFAULT NULL,
  `singer_type` tinyblob,
  `singer_type_id` bigint(20) DEFAULT NULL,
  `singer_words` int(11) DEFAULT NULL,
  PRIMARY KEY (`singer_id`),
  KEY `FK7nflongcv8ash9axo07xgm5xc` (`singer_type_id`),
  CONSTRAINT `FK7nflongcv8ash9axo07xgm5xc` FOREIGN KEY (`singer_type_id`) REFERENCES `tb_singer_type` (`singer_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_singer
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_singer_type`
-- ----------------------------
DROP TABLE IF EXISTS `tb_singer_type`;
CREATE TABLE `tb_singer_type` (
  `singer_type_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `singer_type_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`singer_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_singer_type
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_song`
-- ----------------------------
DROP TABLE IF EXISTS `tb_song`;
CREATE TABLE `tb_song` (
  `song_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lang_id` int(11) DEFAULT NULL,
  `singer_id` varchar(255) DEFAULT NULL,
  `song_clicks` int(11) DEFAULT NULL,
  `song_format` int(11) DEFAULT NULL,
  `song_local_url` varchar(255) DEFAULT NULL,
  `song_name` varchar(255) DEFAULT NULL,
  `song_server_url` varchar(255) DEFAULT NULL,
  `song_spell` varchar(255) DEFAULT NULL,
  `song_version` int(11) DEFAULT NULL,
  `song_words` int(11) DEFAULT NULL,
  PRIMARY KEY (`song_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_song
-- ----------------------------
INSERT INTO `tb_song` VALUES ('1', '1', '1', '1', '1', null, null, null, null, null, null);
INSERT INTO `tb_song` VALUES ('2', '2', '2', '2', '2', null, null, null, null, null, null);

-- ----------------------------
-- Table structure for `tb_user`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('1', 'e10adc3949ba59abbe56e057f20f883e', 'admin');

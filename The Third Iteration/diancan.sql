/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50140
Source Host           : localhost:3306
Source Database       : diancan

Target Server Type    : MYSQL
Target Server Version : 50140
File Encoding         : 65001

Date: 2018-01-06 09:36:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `wct_bill`
-- ----------------------------
DROP TABLE IF EXISTS `wct_bill`;
CREATE TABLE `wct_bill` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `gids` varchar(100) DEFAULT NULL,
  `pirce` varchar(100) DEFAULT NULL,
  `user` varchar(100) DEFAULT NULL,
  `uid` varchar(100) DEFAULT NULL,
  `shop` varchar(100) DEFAULT NULL,
  `bill` varchar(2000) DEFAULT NULL,
  `openid` varchar(255) DEFAULT NULL,
  `ndate` varchar(255) DEFAULT NULL,
  `total` varchar(255) DEFAULT NULL,
  `way` varchar(50) DEFAULT NULL,
  `gnames` varchar(500) DEFAULT NULL,
  `sid` varchar(10) DEFAULT NULL,
  `state` int(1) DEFAULT NULL,
  `statecn` varchar(50) DEFAULT NULL,
  `tel` varchar(50) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `note` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wct_bill
-- ----------------------------
INSERT INTO `wct_bill` VALUES ('67', '11', null, 'liangpeng', '89', '重庆特色菜', null, null, '2018-01-05', '45', null, '麻辣肉丝', '19', null, null, '12345678901', '12345', '');
INSERT INTO `wct_bill` VALUES ('68', '11,12', null, 'liangpeng', '89', '重庆特色菜', null, null, '2018-01-05', '68', null, '麻辣肉丝,麻婆豆腐', '19', null, null, '12345678901', '12345', '');
INSERT INTO `wct_bill` VALUES ('69', '15', null, 'liangpeng', '89', '湖南菜馆', null, null, '2018-01-05', '33', null, '红烧肉', '23', null, null, '12345678901', '12345', '');

-- ----------------------------
-- Table structure for `wct_dingzuo`
-- ----------------------------
DROP TABLE IF EXISTS `wct_dingzuo`;
CREATE TABLE `wct_dingzuo` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `openid` varchar(200) DEFAULT NULL,
  `username` varchar(200) DEFAULT NULL,
  `renshu` varchar(20) DEFAULT NULL,
  `xingming` varchar(200) DEFAULT NULL,
  `shouji` varchar(200) DEFAULT NULL,
  `shijian` varchar(200) DEFAULT NULL,
  `todate` varchar(200) DEFAULT NULL,
  `beizhu` varchar(600) DEFAULT NULL,
  `shopid` varchar(200) DEFAULT NULL,
  `shopname` varchar(200) DEFAULT NULL,
  `ndate` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wct_dingzuo
-- ----------------------------

-- ----------------------------
-- Table structure for `wct_good`
-- ----------------------------
DROP TABLE IF EXISTS `wct_good`;
CREATE TABLE `wct_good` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `gname` varchar(100) DEFAULT NULL,
  `price` varchar(10) DEFAULT NULL,
  `jifen` varchar(10) DEFAULT NULL,
  `note` varchar(500) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `img` varchar(500) DEFAULT NULL,
  `count` varchar(10) DEFAULT '0',
  `typeid` varchar(10) DEFAULT NULL,
  `xiaoliang` int(10) DEFAULT '0',
  `ownid` varchar(10) DEFAULT NULL,
  `sid` varchar(10) DEFAULT NULL,
  `shop` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wct_good
-- ----------------------------
INSERT INTO `wct_good` VALUES ('12', '麻婆豆腐', '3', null, '麻婆豆腐色香味好', null, '30a2b846-e0c9-494f-b293-55931879.gif', '0', '1', null, null, '1', '1L-堂食');
INSERT INTO `wct_good` VALUES ('14', '辣子鸡', '5', null, '非常辣的东西', null, '85acc990-40f3-41bf-a2f3-445691d0.gif', '0', '8', null, null, '1', '1L-堂食');
INSERT INTO `wct_good` VALUES ('15', '红烧肉', '5', null, '好吃不腻', null, '771be324-c42e-4e09-9f9a-fbbfc872.gif', '0', '8', null, null, '23', '23');
INSERT INTO `wct_good` VALUES ('16', '干办凉粉', '6', null, '好吃的干办凉粉呵呵呵', null, 'e4cffef8-0758-49ea-8fc4-ab469aa1.gif', '0', '1', null, null, '19', '重庆特色菜');
INSERT INTO `wct_good` VALUES ('17', '红烧鸭头', '60', null, '好吃的红烧鸭头好吃的红烧鸭头好吃的红烧鸭头好吃的红烧鸭头好吃的红烧鸭头', null, '9175dae3-154d-4b95-b927-2ca9add1.gif', '0', '1', null, null, '19', '重庆特色菜');
INSERT INTO `wct_good` VALUES ('18', '青椒肉片', '5', null, '青椒肉片，有点辣哦', null, 'dcee276b-fab6-424f-ac53-43746b2f.gif', '0', '6', null, null, '1', '1L-堂食');
INSERT INTO `wct_good` VALUES ('19', '干煸四季豆', '32', null, '味道可口的味道可口的味道可口的味道可口的味道可口的', null, 'bb09d9a8-625a-4f14-b935-cb3cede5.gif', '0', '1', null, null, '19', '重庆特色菜');
INSERT INTO `wct_good` VALUES ('20', '回锅肉', '5', null, '肥而不腻肥而不腻', null, '3bdb5483-1a34-4c79-84f4-1583627b.gif', '0', '1', null, null, '1', '1L-堂食');
INSERT INTO `wct_good` VALUES ('21', '腊肠', '5', null, '好吃的腊肠，味甜', null, '772729ac-cefb-4c85-9328-04ac042e.gif', '0', '2', null, null, '1', '1L-堂食');
INSERT INTO `wct_good` VALUES ('22', '细面', '5', null, '', null, '45fb2b23-574a-48c4-ab6e-b77864c4.gif', '0', '1', null, null, '4', '2L-兰州拉面');
INSERT INTO `wct_good` VALUES ('23', '加牛肉', '5', null, '', null, 'f4cf03a3-a5eb-41fe-adff-da8cb6bb.gif', '0', '1', null, null, '4', '2L-兰州拉面');
INSERT INTO `wct_good` VALUES ('24', '加羊肉', '5', null, '', null, 'dd5ea779-87ef-4400-84c2-6f511376.gif', '0', '1', null, null, '4', '2L-兰州拉面');
INSERT INTO `wct_good` VALUES ('25', '刀削面', '5', null, '', null, '2a703768-31c8-49b3-b4fa-47156b68.gif', '0', '9', null, null, '4', '2L-兰州拉面');
INSERT INTO `wct_good` VALUES ('26', '煎饼果子加牛肉', '9', null, '', null, '58ac32a7-1efa-4d95-b319-aaa3976f.gif', '0', '2', null, null, '2', '1L-煎饼果子');
INSERT INTO `wct_good` VALUES ('27', '煎饼果子加香肠', '9', null, '', null, '6cdba956-da1b-46e7-96f3-ec8037d6.gif', '0', '2', null, null, '2', '1L-煎饼果子');
INSERT INTO `wct_good` VALUES ('28', '额外加蛋', '1', null, '', null, '184c041e-a01d-4889-b148-08a1105e.gif', '0', '2', null, null, '2', '1L-煎饼果子');

-- ----------------------------
-- Table structure for `wct_shop`
-- ----------------------------
DROP TABLE IF EXISTS `wct_shop`;
CREATE TABLE `wct_shop` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `sname` varchar(100) DEFAULT NULL,
  `img` varchar(500) DEFAULT NULL,
  `note` varchar(500) DEFAULT NULL,
  `address` varchar(500) DEFAULT NULL,
  `longitude` varchar(100) DEFAULT NULL,
  `latitude` varchar(100) DEFAULT NULL,
  `tel` varchar(100) DEFAULT NULL,
  `ownid` varchar(10) DEFAULT NULL,
  `passwd` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wct_shop
-- ----------------------------
INSERT INTO `wct_shop` VALUES ('1', '1L-堂食', '98e1cadd-834f-4a67-8ccd-01c1a7d0.gif', '这里有很多的家常菜', '食堂1楼', null, null, '18602392323', null, null);
INSERT INTO `wct_shop` VALUES ('2', '1L-煎饼果子', '70f39628-fa81-4fea-88bb-6d86515f.gif', '这里有煎饼果子、烧饼哟', '食堂1楼', null, null, '15608900988', null, null);
INSERT INTO `wct_shop` VALUES ('3', '1L-包点', '8f2ebe70-adbe-43bc-b780-f9e25a1f.gif', '有每天新鲜出炉的蒸馒头、肉包、面包、油条、豆浆哦~', '食堂1楼', null, null, '17808900998', null, null);
INSERT INTO `wct_shop` VALUES ('4', '2L-兰州拉面', '1017f940-e198-487b-bb53-f6cc3f2c.gif', '提供兰州拉面', '食堂2楼', null, null, '18602398823', null, null);
INSERT INTO `wct_shop` VALUES ('5', '2L-瓦罐煨汤', '4fdbf6c1-b671-442e-9a5a-df5923ad.gif', '这里提供乌鸡汤、玉米排骨汤、山药排骨汤等等', '食堂2楼', null, null, '18602398823', null, null);
INSERT INTO `wct_shop` VALUES ('6', '2L-烤肉饭', '37abd170-ec28-4ab7-b85c-e0a4b44a.gif', '这里有烤肉饭 鸡柳饭哦', '食堂2楼', null, null, '18602398823', null, null);

-- ----------------------------
-- Table structure for `wct_type`
-- ----------------------------
DROP TABLE IF EXISTS `wct_type`;
CREATE TABLE `wct_type` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) DEFAULT NULL,
  `ownid` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wct_type
-- ----------------------------
INSERT INTO `wct_type` VALUES ('1', '川菜', null);
INSERT INTO `wct_type` VALUES ('2', '广东菜', null);
INSERT INTO `wct_type` VALUES ('6', '江西菜', null);
INSERT INTO `wct_type` VALUES ('8', '湖南菜', null);
INSERT INTO `wct_type` VALUES ('9', '清真', null);

-- ----------------------------
-- Table structure for `wct_user`
-- ----------------------------
DROP TABLE IF EXISTS `wct_user`;
CREATE TABLE `wct_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `passwd` varchar(50) DEFAULT NULL,
  `roletype` varchar(50) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `tel` varchar(50) DEFAULT NULL,
  `qq` varchar(20) DEFAULT NULL,
  `wechat` varchar(20) DEFAULT NULL,
  `sex` varchar(20) DEFAULT NULL,
  `birth` varchar(20) DEFAULT NULL,
  `img` varchar(200) DEFAULT NULL,
  `sid` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=92 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wct_user
-- ----------------------------
INSERT INTO `wct_user` VALUES ('59', 'admin', 'admin', '1', '', null, null, null, null, null, null, null, null);
INSERT INTO `wct_user` VALUES ('90', '2L-瓦罐煨汤', '111111', '3', null, null, null, null, null, null, null, null, '24');
INSERT INTO `wct_user` VALUES ('91', '2L-烤肉饭', '111111', '3', null, null, null, null, null, null, null, null, '25');
INSERT INTO `wct_user` VALUES ('89', 'liangpeng', '123', '2', '', '12345', '12345678901', '', '', '男', '', null, null);

-- ----------------------------
-- Table structure for `wct_vip`
-- ----------------------------
DROP TABLE IF EXISTS `wct_vip`;
CREATE TABLE `wct_vip` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `qq` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `openid` varchar(255) DEFAULT NULL,
  `vname` varchar(255) DEFAULT NULL,
  `wname` varchar(255) DEFAULT NULL,
  `address` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wct_vip
-- ----------------------------
INSERT INTO `wct_vip` VALUES ('1', '543548596', '男', '15123384884', '123', '波仔', null, null);

/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80039
 Source Host           : localhost:3306
 Source Schema         : blibliweb

 Target Server Type    : MySQL
 Target Server Version : 80039
 File Encoding         : 65001

 Date: 15/12/2024 19:07:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文章标题',
  `content` varchar(10000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文章内容',
  `cover_img` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文章封面',
  `state` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '草稿' COMMENT '文章状态: 只能是[已发布] 或者 [草稿]',
  `category_id` int UNSIGNED NULL DEFAULT NULL COMMENT '文章分类ID',
  `create_user` int UNSIGNED NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_article_category`(`category_id` ASC) USING BTREE,
  INDEX `fk_article_user`(`create_user` ASC) USING BTREE,
  CONSTRAINT `fk_article_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_article_user` FOREIGN KEY (`create_user`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (15, '紫罗兰', '<p><br></p>', 'http://sncodwj9m.hn-bkt.clouddn.com/8590b561-cf0e-4c88-9766-0b95ac98c61f.jpg', '已发布', 16, 10, '2024-11-24 22:32:23', '2024-11-24 22:32:23');
INSERT INTO `article` VALUES (16, 'Ligg', '<p>背景...爱去哪去哪...</p>', 'http://sncodwj9m.hn-bkt.clouddn.com/d24497df-a48c-4b52-b3fb-d41e51dab016.jpg', '已发布', 17, 10, '2024-11-24 22:34:06', '2024-11-24 22:34:06');
INSERT INTO `article` VALUES (40, '败犬女12313', '<p>背景...爱去哪去哪...</p>', 'https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png', '已发布', 16, 46, '2024-12-10 20:04:30', '2024-12-10 20:04:30');
INSERT INTO `article` VALUES (42, '败犬女主11', '<p>达娃大大大大</p>', 'http://sncodwj9m.hn-bkt.clouddn.com/59bcde37-65b7-4614-84f1-94471401a9df.jpg', '已发布', 20, 46, '2024-12-10 20:07:24', '2024-12-10 20:07:24');
INSERT INTO `article` VALUES (43, '阿瓦大大', '<p>达娃大大大大aad</p>', 'http://sncodwj9m.hn-bkt.clouddn.com/a7b82b2e-01ab-4e64-a3a2-a2d2ec158cd3.jpg', '已发布', 40, 46, '2024-12-10 20:07:50', '2024-12-10 20:07:50');
INSERT INTO `article` VALUES (45, '北京旅游攻略', '天安门,颐和园,鸟巢,长城...爱去哪去哪...', 'https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png', '已发布', 42, 46, '2024-12-10 20:12:02', '2024-12-10 20:12:02');

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `category_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名称',
  `category_alias` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类别名',
  `create_user` int UNSIGNED NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_category_user`(`create_user` ASC) USING BTREE,
  CONSTRAINT `fk_category_user` FOREIGN KEY (`create_user`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (16, '军事', 'js', 10, '2024-11-24 22:31:04', '2024-12-07 17:38:02');
INSERT INTO `category` VALUES (17, '博客', 'bing', 10, '2024-11-24 22:31:19', '2024-11-24 22:31:19');
INSERT INTO `category` VALUES (19, 'wqe111', 'js1111', 46, '2024-12-07 16:10:16', '2024-12-10 15:10:40');
INSERT INTO `category` VALUES (20, '博客码', 'bing', 46, '2024-12-07 16:18:04', '2024-12-07 16:18:04');
INSERT INTO `category` VALUES (24, '123', '123', 4, '2024-12-07 19:27:29', '2024-12-07 19:27:29');
INSERT INTO `category` VALUES (40, '131', '13131', 46, '2024-12-10 15:10:24', '2024-12-10 15:10:24');
INSERT INTO `category` VALUES (41, '12313', '13131', 46, '2024-12-10 15:10:33', '2024-12-10 15:10:33');
INSERT INTO `category` VALUES (42, '问问请', '请问企鹅群', 46, '2024-12-10 15:11:23', '2024-12-10 15:11:23');
INSERT INTO `category` VALUES (43, 'qweqe', 'qeqwe', 46, '2024-12-13 00:10:12', '2024-12-13 00:10:12');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `nickname` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '昵称',
  `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户介绍',
  `email` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '邮箱',
  `user_pic` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '头像',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 65 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (4, 'qwerty56', '827ccb0eea8a706c4c34a16891f84e7b', '123wba', NULL, 'wacc@qq.com', '', '2024-11-20 02:26:15', '2024-12-06 20:41:57');
INSERT INTO `user` VALUES (6, '123456', 'e10adc3949ba59abbe56e057f20f883e', 'ligg', NULL, '', 'http://sncodwj9m.hn-bkt.clouddn.com/824c9838-acd7-466f-b58d-91e637322448.jpg', '2024-11-20 02:33:24', '2024-11-20 02:33:24');
INSERT INTO `user` VALUES (9, 'lwz123', '827ccb0eea8a706c4c34a16891f84e7b', '', NULL, '', 'https://123.npg123', '2024-11-21 00:11:00', '2024-11-21 00:27:48');
INSERT INTO `user` VALUES (10, '111111', 'e3ceb5881a0a1fdaad01296d7554868d', 'ligg1111', NULL, 'wenzhouli@gmail.com', 'http://sncodwj9m.hn-bkt.clouddn.com/824c9838-acd7-466f-b58d-91e637322448.jpg', '2024-11-24 02:33:45', '2024-12-08 13:41:37');
INSERT INTO `user` VALUES (46, 'ligg200309', 'e10adc3949ba59abbe56e057f20f883e', '萌主', NULL, 'zs@163.com', 'http://sncodwj9m.hn-bkt.clouddn.com/2b573fb7-a81a-4166-80bc-14b0bce5964c.jpg', '2024-12-07 16:05:44', '2024-12-11 17:56:39');
INSERT INTO `user` VALUES (60, 'qweqwe', '96e79218965eb72c92a549dd5a330112', '', NULL, '', '', '2024-12-12 17:39:41', '2024-12-12 17:39:41');
INSERT INTO `user` VALUES (61, 'ligg2003091', 'e10adc3949ba59abbe56e057f20f883e', '', NULL, '', '', '2024-12-12 17:52:48', '2024-12-12 17:52:48');
INSERT INTO `user` VALUES (62, '123111111', '4297f44b13955235245b2497399d7a93', '', NULL, '', '', '2024-12-12 17:55:18', '2024-12-12 17:55:18');
INSERT INTO `user` VALUES (63, 'ligg20030911', '96e79218965eb72c92a549dd5a330112', '', NULL, '', '', '2024-12-12 17:58:51', '2024-12-12 17:58:51');
INSERT INTO `user` VALUES (64, 'qweqeqeq', '96e79218965eb72c92a549dd5a330112', '', NULL, '', '', '2024-12-12 18:00:58', '2024-12-12 18:00:58');
INSERT INTO `user` VALUES (65, 'qweeqqe11', '7fa8282ad93047a4d6fe6111c93b308a', '', NULL, '', '', '2024-12-15 01:43:55', '2024-12-15 01:43:55');

-- ----------------------------
-- Table structure for user_video
-- ----------------------------
DROP TABLE IF EXISTS `user_video`;
CREATE TABLE `user_video`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '视频封面',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '视屏简介',
  `user_id` int UNSIGNED NULL DEFAULT NULL,
  `video_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '视频链接',
  `create_time` datetime NULL DEFAULT NULL COMMENT '发布时间\r\n',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_video_user_id_fk`(`user_id` ASC) USING BTREE,
  CONSTRAINT `user_video_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_video
-- ----------------------------
INSERT INTO `user_video` VALUES (4, '青春恋爱物语果然有问题', 'http://sncodwj9m.hn-bkt.clouddn.com/b546e174-33f4-42be-bdd2-e3ac690bdf24.jpg', '青春恋爱物语果然有问题', NULL, NULL, NULL, '2024-12-14 17:57:38');
INSERT INTO `user_video` VALUES (12, '败犬女2131313主', 'http://sncodwj9m.hn-bkt.clouddn.com/62a00dbc-83de-4466-93b7-4b536ee7362f.jpg', 'qeqeqeqeqeq', NULL, NULL, NULL, NULL);
INSERT INTO `user_video` VALUES (25, '青春恋爱物语果然有问题', 'http://sncodwj9m.hn-bkt.clouddn.com/b546e174-33f4-42be-bdd2-e3ac690bdf24.jpg', '青春恋爱物语果然有问题', 46, NULL, '2024-12-13 21:48:24', '2024-12-13 22:20:27');
INSERT INTO `user_video` VALUES (28, '缘结甘神家', 'https://lain.bgm.tv/r/400/pic/cover/l/7b/b8/431370_H6j06.jpg', '以京大医学部为目标的高中生上终瓜生。在儿童福利院长大的瓜生，被位于京都的神社“甘神神社”的宫司收养，寄居于', 46, NULL, '2024-12-13 22:24:41', '2024-12-13 22:24:41');
INSERT INTO `user_video` VALUES (29, '败犬女主', 'https://lain.bgm.tv/r/400/pic/cover/l/e4/dc/464376_NsZRw.jpg', '以京大医学部为目标的高中生上终瓜生。在儿童福利院长大的瓜生，被位于京都的神社“甘神神社”的宫司收养，寄居于', 46, 'https://play.xfvod.pro/temp/2407/%E8%B4%A5%E5%8C%9702z.mp4', '2024-12-13 22:31:20', '2024-12-13 22:39:42');
INSERT INTO `user_video` VALUES (30, '缘结甘神家', 'https://lain.bgm.tv/r/400/pic/cover/l/7b/b8/431370_H6j06.jpg', '以京大医学部为目标的高中生上终瓜生。在儿童福利院长大的瓜生，被位于京都的神社“甘神神社”的宫司收养，寄居于', 46, NULL, '2024-12-13 22:47:52', '2024-12-13 22:47:52');
INSERT INTO `user_video` VALUES (34, '青春恋爱物语果然有问题', 'http://sncodwj9m.hn-bkt.clouddn.com/b546e174-33f4-42be-bdd2-e3ac690bdf24.jpg', '青春恋爱物语果然有问题', 46, NULL, '2024-12-14 21:04:40', '2024-12-14 21:16:20');
INSERT INTO `user_video` VALUES (35, '青之箱', 'http://sncodwj9m.hn-bkt.clouddn.com/c1ee1126-96f3-4a13-8aa3-df145a03cf18.jpg', '猪股大喜，高中一年级，是初高中一体的体育强校——荣明高中的男子羽毛球社成员。', 46, NULL, '2024-12-14 21:17:47', '2024-12-14 21:17:47');
INSERT INTO `user_video` VALUES (36, '链锯人', 'http://sncodwj9m.hn-bkt.clouddn.com/ac450b90-f2aa-4322-a321-19ef1fbd7147.jpg', '　电次是个背负了父亲遗留下的庞大债务，因此过着极端贫穷生活的少年，他救了恶魔啵奇塔，并与它一起以恶魔猎人的身份斩杀低阶恶魔来换取酬劳。有一天，流氓骗了电次，让他成为一堆恶魔的祭品，电次在临死之际，啵奇塔牺牲自己，把心脏给了他，让他复活并拥有了恶魔的力量，只要一拉位在心脏那边的链子就能发动。之后电次被公安人员看上，因此成为公安的恶魔猎人……', 46, NULL, '2024-12-14 21:18:23', '2024-12-14 21:18:23');
INSERT INTO `user_video` VALUES (37, 'qwweqeqeq', 'http://sncodwj9m.hn-bkt.clouddn.com/8cb2b943-057b-4e93-af70-7a44d96d58af.jpg', '委屈恶气恶气恶气恶气', 46, NULL, '2024-12-15 01:41:47', '2024-12-15 01:41:47');
INSERT INTO `user_video` VALUES (38, '企鹅企鹅企鹅去', 'http://sncodwj9m.hn-bkt.clouddn.com/39f2840c-19ea-41de-ac83-bb501081e319.jpg', '去恶气恶气恶气恶气', 46, NULL, '2024-12-15 01:41:55', '2024-12-15 01:41:55');

SET FOREIGN_KEY_CHECKS = 1;

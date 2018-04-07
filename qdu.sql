/*
Navicat MySQL Data Transfer

Source Server         : YJ
Source Server Version : 50721
Source Host           : 47.94.205.153:3306
Source Database       : qdu

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-04-07 11:03:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `class`
-- ----------------------------
DROP TABLE IF EXISTS `class`;
CREATE TABLE `class` (
  `class_id` int(11) NOT NULL AUTO_INCREMENT,
  `class_name` varchar(255) NOT NULL,
  `teacher_id` int(11) NOT NULL,
  `teacher_name` varchar(255) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`class_id`),
  KEY `user_id` (`teacher_id`),
  CONSTRAINT `class_ibfk_1` FOREIGN KEY (`teacher_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of class
-- ----------------------------

-- ----------------------------
-- Table structure for `file`
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file` (
  `file_id` int(11) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(255) NOT NULL,
  `file_path` varchar(255) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `class_id` int(11) NOT NULL,
  `upload_time` datetime NOT NULL,
  PRIMARY KEY (`file_id`),
  KEY `class_id` (`class_id`),
  CONSTRAINT `file_ibfk_1` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of file
-- ----------------------------

-- ----------------------------
-- Table structure for `homework`
-- ----------------------------
DROP TABLE IF EXISTS `homework`;
CREATE TABLE `homework` (
  `homework_id` int(11) NOT NULL AUTO_INCREMENT,
  `questions_id` int(11) NOT NULL,
  `full_score` int(11) NOT NULL,
  `create_time` date NOT NULL,
  PRIMARY KEY (`homework_id`),
  KEY `questions_id` (`questions_id`),
  CONSTRAINT `homework_ibfk_1` FOREIGN KEY (`questions_id`) REFERENCES `question` (`question_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of homework
-- ----------------------------

-- ----------------------------
-- Table structure for `homework_class`
-- ----------------------------
DROP TABLE IF EXISTS `homework_class`;
CREATE TABLE `homework_class` (
  `homework_id` int(11) NOT NULL,
  `class_id` int(11) NOT NULL,
  `deadline` datetime NOT NULL,
  KEY `homework_id` (`homework_id`),
  KEY `class_id` (`class_id`),
  CONSTRAINT `homework_class_ibfk_1` FOREIGN KEY (`homework_id`) REFERENCES `homework` (`homework_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `homework_class_ibfk_2` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of homework_class
-- ----------------------------

-- ----------------------------
-- Table structure for `question`
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `question_id` int(11) NOT NULL AUTO_INCREMENT,
  `question_type` varchar(20) NOT NULL,
  `question_content` varchar(200) NOT NULL,
  `question_img` varchar(200) DEFAULT NULL,
  `option_A` varchar(200) DEFAULT NULL,
  `option_B` varchar(200) DEFAULT NULL,
  `option_C` varchar(200) DEFAULT NULL,
  `option_D` varchar(200) DEFAULT NULL,
  `correct_option` varchar(20) DEFAULT NULL,
  `question_score` int(11) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of question
-- ----------------------------

-- ----------------------------
-- Table structure for `student_class`
-- ----------------------------
DROP TABLE IF EXISTS `student_class`;
CREATE TABLE `student_class` (
  `class_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  KEY `class_id` (`class_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `student_class_ibfk_1` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `student_class_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of student_class
-- ----------------------------

-- ----------------------------
-- Table structure for `student_homework`
-- ----------------------------
DROP TABLE IF EXISTS `student_homework`;
CREATE TABLE `student_homework` (
  `homework_id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  `student_answer` varchar(400) NOT NULL,
  `score` int(11) NOT NULL,
  `subtime_time` datetime NOT NULL,
  KEY `homework_id` (`homework_id`),
  KEY `student_id` (`student_id`),
  CONSTRAINT `student_homework_ibfk_1` FOREIGN KEY (`homework_id`) REFERENCES `homework` (`homework_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `student_homework_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of student_homework
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(40) NOT NULL,
  `user_password` varchar(100) NOT NULL,
  `user_phone` varchar(20) NOT NULL,
  `user_type` varchar(20) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `unique_phone` (`user_phone`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('5', 'chen', '202cb962ac59075b964b07152d234b70', '17611226230', 'student', '2018-03-18 17:28:34');
INSERT INTO `user` VALUES ('6', '哈哈哈', 'ee7ddfa19482e219fb5021ec30bd975c', '15621009628', 'student', '2018-03-18 19:04:21');
INSERT INTO `user` VALUES ('7', '王朝', 'af99593cf21078621a0c5c868705ecc4', '17854264315', 'student', '2018-03-18 19:11:34');
INSERT INTO `user` VALUES ('8', '无敌', '4607e782c4d86fd5364d7e4508bb10d9', '17611250210', 'teacher', '2018-04-05 16:18:00');
INSERT INTO `user` VALUES ('9', '洁浊', '4607e782c4d86fd5364d7e4508bb10d9', '13586081528', 'teacher', '2018-04-05 16:12:30');

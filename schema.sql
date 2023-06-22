/*
 Navicat Premium Data Transfer

 Source Server         : LOCAL_DOCKER
 Source Server Type    : MySQL
 Source Server Version : 80033 (8.0.33)
 Source Host           : mysql:3306
 Source Schema         : book_rooms

 Target Server Type    : MySQL
 Target Server Version : 80033 (8.0.33)
 File Encoding         : 65001

 Date: 19/06/2023 14:19:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity_logs
-- ----------------------------
DROP TABLE IF EXISTS `activity_logs`;
CREATE TABLE `activity_logs` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `external_type` varchar(255) DEFAULT NULL COMMENT 'polymorphic for foreign table name',
  `external_id` int DEFAULT NULL COMMENT 'polymorphic for foreign id',
  `data` longtext,
  `description` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `activity_logs_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of activity_logs
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for consumption_reservation
-- ----------------------------
DROP TABLE IF EXISTS `consumption_reservation`;
CREATE TABLE `consumption_reservation` (
  `consumption_id` int DEFAULT NULL,
  `reservation_id` int DEFAULT NULL,
  `qty` int DEFAULT NULL,
  KEY `consumption_id` (`consumption_id`),
  KEY `reservation_id` (`reservation_id`),
  CONSTRAINT `consumption_reservation_ibfk_1` FOREIGN KEY (`consumption_id`) REFERENCES `consumptions` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `consumption_reservation_ibfk_2` FOREIGN KEY (`reservation_id`) REFERENCES `reservations` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of consumption_reservation
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for consumptions
-- ----------------------------
DROP TABLE IF EXISTS `consumptions`;
CREATE TABLE `consumptions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of consumptions
-- ----------------------------
BEGIN;
INSERT INTO `consumptions` (`id`, `name`, `created_at`, `updated_at`) VALUES (1, 'Air Mineral', '2023-06-15 11:21:02', '2023-06-15 11:21:02');
INSERT INTO `consumptions` (`id`, `name`, `created_at`, `updated_at`) VALUES (2, 'Snack', '2023-06-15 11:21:02', '2023-06-15 11:21:02');
INSERT INTO `consumptions` (`id`, `name`, `created_at`, `updated_at`) VALUES (3, 'Nasi Padang', '2023-06-15 11:21:02', '2023-06-15 11:21:02');
COMMIT;

-- ----------------------------
-- Table structure for facilites
-- ----------------------------
DROP TABLE IF EXISTS `facilites`;
CREATE TABLE `facilites` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of facilites
-- ----------------------------
BEGIN;
INSERT INTO `facilites` (`id`, `name`, `created_at`, `updated_at`) VALUES (1, 'AC', '2023-06-15 01:15:17', '2023-06-15 01:15:17');
INSERT INTO `facilites` (`id`, `name`, `created_at`, `updated_at`) VALUES (2, 'Proyektor', '2023-06-15 01:15:17', '2023-06-15 01:15:17');
INSERT INTO `facilites` (`id`, `name`, `created_at`, `updated_at`) VALUES (3, 'Meja', '2023-06-15 01:15:17', '2023-06-15 01:15:17');
INSERT INTO `facilites` (`id`, `name`, `created_at`, `updated_at`) VALUES (4, 'Kursi', '2023-06-15 01:15:17', '2023-06-15 01:15:17');
COMMIT;

-- ----------------------------
-- Table structure for facilities
-- ----------------------------
DROP TABLE IF EXISTS `facilities`;
CREATE TABLE `facilities` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of facilities
-- ----------------------------
BEGIN;
INSERT INTO `facilities` (`id`, `name`, `created_at`, `updated_at`) VALUES (1, 'AC', '2023-06-15 11:21:02', '2023-06-15 11:21:02');
INSERT INTO `facilities` (`id`, `name`, `created_at`, `updated_at`) VALUES (2, 'Proyektor', '2023-06-15 11:21:02', '2023-06-15 11:21:02');
INSERT INTO `facilities` (`id`, `name`, `created_at`, `updated_at`) VALUES (3, 'Meja', '2023-06-15 11:21:02', '2023-06-15 11:21:02');
INSERT INTO `facilities` (`id`, `name`, `created_at`, `updated_at`) VALUES (4, 'Kursi', '2023-06-15 11:21:02', '2023-06-15 11:30:46');
COMMIT;

-- ----------------------------
-- Table structure for facility_room
-- ----------------------------
DROP TABLE IF EXISTS `facility_room`;
CREATE TABLE `facility_room` (
  `facility_id` int DEFAULT NULL,
  `room_id` int DEFAULT NULL,
  `qty` int DEFAULT NULL,
  KEY `facility_id` (`facility_id`),
  KEY `room_id` (`room_id`),
  CONSTRAINT `facility_room_ibfk_1` FOREIGN KEY (`facility_id`) REFERENCES `facilities` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `facility_room_ibfk_2` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of facility_room
-- ----------------------------
BEGIN;
INSERT INTO `facility_room` (`facility_id`, `room_id`, `qty`) VALUES (1, 1, 4);
INSERT INTO `facility_room` (`facility_id`, `room_id`, `qty`) VALUES (2, 1, 1);
INSERT INTO `facility_room` (`facility_id`, `room_id`, `qty`) VALUES (3, 1, 40);
INSERT INTO `facility_room` (`facility_id`, `room_id`, `qty`) VALUES (4, 1, 40);
INSERT INTO `facility_room` (`facility_id`, `room_id`, `qty`) VALUES (1, 2, 2);
INSERT INTO `facility_room` (`facility_id`, `room_id`, `qty`) VALUES (2, 2, 1);
INSERT INTO `facility_room` (`facility_id`, `room_id`, `qty`) VALUES (3, 2, 20);
INSERT INTO `facility_room` (`facility_id`, `room_id`, `qty`) VALUES (4, 2, 20);
INSERT INTO `facility_room` (`facility_id`, `room_id`, `qty`) VALUES (1, 3, 1);
INSERT INTO `facility_room` (`facility_id`, `room_id`, `qty`) VALUES (2, 3, 2);
INSERT INTO `facility_room` (`facility_id`, `room_id`, `qty`) VALUES (3, 3, 10);
INSERT INTO `facility_room` (`facility_id`, `room_id`, `qty`) VALUES (4, 3, 10);
COMMIT;

-- ----------------------------
-- Table structure for reservations
-- ----------------------------
DROP TABLE IF EXISTS `reservations`;
CREATE TABLE `reservations` (
  `id` int NOT NULL AUTO_INCREMENT,
  `room_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `attendance` int DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `started_at` timestamp NULL DEFAULT NULL,
  `ended_at` timestamp NULL DEFAULT NULL,
  `checked_in_at` timestamp NULL DEFAULT NULL,
  `checked_out_at` timestamp NULL DEFAULT NULL,
  `status` int DEFAULT '0' COMMENT '0 for booked\n1 for checked in\n2 for checked out\n3 for canceled',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `room_id` (`room_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `reservations_ibfk_1` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`),
  CONSTRAINT `reservations_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of reservations
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for rooms
-- ----------------------------
DROP TABLE IF EXISTS `rooms`;
CREATE TABLE `rooms` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type_id` int DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `capacity` int DEFAULT NULL,
  `description` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `type_id` (`type_id`),
  CONSTRAINT `rooms_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `types` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of rooms
-- ----------------------------
BEGIN;
INSERT INTO `rooms` (`id`, `type_id`, `name`, `capacity`, `description`, `created_at`, `updated_at`) VALUES (1, 2, 'Ruangan A', 40, 'Dapat digunakan dalam segala kondisi', '2023-06-15 11:21:03', '2023-06-15 11:21:03');
INSERT INTO `rooms` (`id`, `type_id`, `name`, `capacity`, `description`, `created_at`, `updated_at`) VALUES (2, 2, 'Ruangan B', 20, 'Dapat digunakan untuk skala kecil', '2023-06-15 11:21:03', '2023-06-15 11:21:03');
INSERT INTO `rooms` (`id`, `type_id`, `name`, `capacity`, `description`, `created_at`, `updated_at`) VALUES (3, 1, 'Balai A', 100, 'Dapat digunakakn untuk resepsi pernikahan', '2023-06-15 11:21:03', '2023-06-15 11:21:03');
COMMIT;

-- ----------------------------
-- Table structure for types
-- ----------------------------
DROP TABLE IF EXISTS `types`;
CREATE TABLE `types` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `is_default` tinyint DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COMMENT='we need 2 default room types:\n1. Gedung\n2. Ruangan\n\nso the employee is able to add more room types,\nsince "rooms" is not necassarily a room, it can\nbe a building or even an ambulance, who knows.';

-- ----------------------------
-- Records of types
-- ----------------------------
BEGIN;
INSERT INTO `types` (`id`, `name`, `is_default`, `created_at`, `updated_at`) VALUES (1, 'Gedung', 1, '2023-06-15 11:21:03', '2023-06-15 11:21:03');
INSERT INTO `types` (`id`, `name`, `is_default`, `created_at`, `updated_at`) VALUES (2, 'Ruangan', 1, '2023-06-15 11:21:03', '2023-06-15 11:21:03');
COMMIT;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL COMMENT 'hashed password',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of users
-- ----------------------------
BEGIN;
INSERT INTO `users` (`id`, `name`, `username`, `password`, `created_at`, `updated_at`) VALUES (1, 'Admin', 'admin', '5ebe2294ecd0e0f08eab7690d2a6ee69', '2023-06-15 11:21:03', '2023-06-15 11:21:03');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

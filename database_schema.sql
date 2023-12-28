/*
 Navicat MySQL Data Transfer

 Source Server         : neptrueworks
 Source Server Type    : MySQL
 Source Server Version : 80100 (8.1.0)
 Source Host           : localhost:3306
 Source Schema         : neptrueworks_order_management

 Target Server Type    : MySQL
 Target Server Version : 80100 (8.1.0)
 File Encoding         : 65001

 Date: 25/12/2023 09:23:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for order_item
-- ----------------------------
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `product_id` int UNSIGNED NOT NULL,
  `quantity` int NULL DEFAULT 1,
  `order_manifest_id` int UNSIGNED NOT NULL,
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` tinytext CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL,
  `last_modified_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_modified_by` tinytext CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL,
  `is_deleted` bit(1) NULL DEFAULT b'0',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_product_id`(`product_id` ASC) USING BTREE,
  INDEX `idx_order_manifest_id`(`order_manifest_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_zh_0900_as_cs ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for order_manifest
-- ----------------------------
DROP TABLE IF EXISTS `order_manifest`;
CREATE TABLE `order_manifest`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `gross_amount` decimal(10, 2) NULL DEFAULT 0.00,
  `paid_at` datetime NULL DEFAULT NULL,
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` tinytext CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL,
  `last_modified_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_modified_by` tinytext CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL,
  `is_deleted` bit(1) NULL DEFAULT b'0',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_gross_amount`(`gross_amount` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_zh_0900_as_cs ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL,
  `price` decimal(10, 2) NOT NULL,
  `stock` int UNSIGNED NOT NULL DEFAULT 0,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` tinytext CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL,
  `last_modified_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_modified_by` tinytext CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL,
  `is_deleted` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_price`(`price` ASC) USING BTREE,
  INDEX `idx_stock`(`stock` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_zh_0900_as_cs ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;

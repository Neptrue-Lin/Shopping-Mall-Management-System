SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for order_item
-- ----------------------------
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item`
(
    `id`                int UNSIGNED NOT NULL AUTO_INCREMENT,
    `product_id`        int UNSIGNED NOT NULL,
    `quantity`          int          NULL DEFAULT 1,
    `order_manifest_id` int UNSIGNED NOT NULL,
    `created_at`        datetime     NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by`        tinytext     NOT NULL,
    `last_modified_at`  datetime     NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `last_modified_by`  tinytext     NOT NULL,
    `is_deleted`        bit(1)       NULL DEFAULT '0',
    PRIMARY KEY (`id`)
);

CREATE INDEX `idx_product_id` ON `order_item`(`product_id`);
CREATE INDEX `idx_order_manifest_id` ON `order_item`(`order_manifest_id`);

-- ----------------------------
-- Table structure for order_manifest
-- ----------------------------
DROP TABLE IF EXISTS `order_manifest`;
CREATE TABLE `order_manifest`
(
    `id`               int UNSIGNED   NOT NULL AUTO_INCREMENT,
    `gross_amount`     decimal(10, 2) NULL DEFAULT 0.00,
    `paid_at`          datetime       NULL DEFAULT NULL,
    `created_at`       datetime       NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by`       tinytext       NOT NULL,
    `last_modified_at` datetime       NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `last_modified_by` tinytext       NOT NULL,
    `is_deleted`       bit(1)         NULL DEFAULT '0',
    PRIMARY KEY (`id`)
);

CREATE INDEX `idx_gross_amount` ON `order_manifest`(`gross_amount`);


-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`
(
    `id`               int UNSIGNED   NOT NULL AUTO_INCREMENT,
    `name`             varchar(255)   NOT NULL,
    `price`            decimal(10, 2) NOT NULL,
    `stock`            int UNSIGNED   NOT NULL DEFAULT 0,
    `created_at`       datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by`       tinytext       NOT NULL,
    `last_modified_at` datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `last_modified_by` tinytext       NOT NULL,
    `is_deleted`       bit(1)         NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`)
);

CREATE INDEX `idx_price` ON `product`(`price`);
CREATE INDEX `idx_stock` ON `product`(`stock`);

SET FOREIGN_KEY_CHECKS = 1;
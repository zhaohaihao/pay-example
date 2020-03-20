-- 用户表结构
DROP TABLE IF EXISTS `mall_user`;
CREATE TABLE `mall_user` (
	`id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
	`username` VARCHAR(50) NOT NULL COMMENT '用户名',
	`password` VARCHAR(50) NOT NULL COMMENT '用户密码, MD5加密',
	`email` VARCHAR(50) DEFAULT NULL,
	`phone` VARCHAR(20) DEFAULT NULL,
	`question` VARCHAR(100) DEFAULT NULL COMMENT '找回密码问题',
	`answer` VARCHAR(100) DEFAULT NULL COMMENT '找回密码答案',
	`role` INT(4) NOT NULL COMMENT '角色: 0-管理员, 1-普通用户',
	`create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`id`),
	UNIQUE KEY `user_name_unique` (`username`) USING BTREE
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- 分类表结构
DROP TABLE IF EXISTS `mall_category`;
CREATE TABLE `mall_category` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '类别ID',
  `parent_id` INT(11) DEFAULT NULL COMMENT '父类别ID, 当ID=0时说明是根节点, 一级类别',
  `name` VARCHAR(50) DEFAULT NULL COMMENT '类别名称',
  `status` TINYINT(1) DEFAULT '1' COMMENT '类别状态: 1-正常, 2-已废弃',
  `sort_order` INT(4) DEFAULT NULL COMMENT '排序编号, 同类展示顺序, 数值相等则自然排序',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 产品表结构
DROP TABLE IF EXISTS `mall_product`;
CREATE TABLE `mall_product` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `category_id` INT(11) NOT NULL COMMENT '分类ID, 对应mall_category表的主键',
  `name` VARCHAR(100) NOT NULL COMMENT '商品名称',
  `subtitle` VARCHAR(200) DEFAULT NULL COMMENT '商品副标题',
  `main_image` VARCHAR(500) DEFAULT NULL COMMENT '产品主图, url相对地址',
  `sub_images` text COMMENT '图片地址, json格式, 扩展用',
  `detail` text COMMENT '商品详情',
  `price` DECIMAL(20,2) NOT NULL COMMENT '价格, 单位-元保留两位小数',
  `stock` INT(11) NOT NULL COMMENT '库存数量',
  `status` INT(6) DEFAULT '1' COMMENT '商品状态: 1-在售 2-下架 3-删除',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- 支付信息表结构
DROP TABLE IF EXISTS `mall_pay_info`;
CREATE TABLE `mall_pay_info` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '支付信息ID',
  `user_id` INT(11) DEFAULT NULL COMMENT '用户ID',
  `order_no` BIGINT(20) NOT NULL COMMENT '订单号',
  `pay_platform` INT(10) DEFAULT NULL COMMENT '支付平台: 1-支付宝, 2-微信',
  `platform_number` VARCHAR(200) DEFAULT NULL COMMENT '支付平台支付流水号',
  `platform_status` VARCHAR(20) DEFAULT NULL COMMENT '支付平台支付状态',
  `pay_amount` DECIMAL(20,2) NOT NULL COMMENT '支付金额',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uqe_order_no` (`order_no`),
  UNIQUE KEY `uqe_platform_number` (`platform_number`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- 订单表结构
DROP TABLE IF EXISTS `mall_order`;
CREATE TABLE `mall_order` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `order_no` BIGINT(20) DEFAULT NULL COMMENT '订单号',
  `user_id` INT(11) DEFAULT NULL COMMENT '用户ID',
  `shipping_id` INT(11) DEFAULT NULL COMMENT '收获地址ID',
  `payment` DECIMAL(20,2) DEFAULT NULL COMMENT '实际付款金额, 单位是元, 保留两位小数',
  `payment_type` INT(4) DEFAULT NULL COMMENT '支付类型: 1-在线支付',
  `postage` INT(10) DEFAULT NULL COMMENT '运费, 单位是元',
  `status` INT(10) DEFAULT NULL COMMENT '订单状态: 0-已取消, 10-未付款, 20-已付款, 40-已发货, 50-交易成功, 60-交易关闭',
  `payment_time` datetime DEFAULT NULL COMMENT '支付时间',
  `send_time` datetime DEFAULT NULL COMMENT '发货时间',
  `end_time` datetime DEFAULT NULL COMMENT '交易完成时间',
  `close_time` datetime DEFAULT NULL COMMENT '交易关闭时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_no_index` (`order_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- 订单明细表结构
DROP TABLE IF EXISTS `mall_order_item`;
CREATE TABLE `mall_order_item` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '订单子表ID',
  `user_id` INT(11) DEFAULT NULL COMMENT '用户ID',
  `order_no` BIGINT(20) DEFAULT NULL COMMENT '订单号',
  `product_id` INT(11) DEFAULT NULL COMMENT '商品ID',
  `product_name` VARCHAR(100) DEFAULT NULL COMMENT '商品名称',
  `product_image` VARCHAR(500) DEFAULT NULL COMMENT '商品图片地址',
  `current_unit_price` DECIMAL(20,2) DEFAULT NULL COMMENT '生成订单时的商品单价, 单位是元, 保留两位小数',
  `quantity` INT(10) DEFAULT NULL COMMENT '商品数量',
  `total_price` DECIMAL(20,2) DEFAULT NULL COMMENT '商品总价, 单位是元, 保留两位小数',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `order_no_index` (`order_no`) USING BTREE,
  KEY `order_no_user_id_index` (`user_id`,`order_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- 收获地址表结构
DROP TABLE IF EXISTS `mall_shipping`;
CREATE TABLE `mall_shipping` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '收获地址ID',
  `user_id` INT(11) DEFAULT NULL COMMENT '用户ID',
  `receiver_name` VARCHAR(20) DEFAULT NULL COMMENT '收货姓名',
  `receiver_phone` VARCHAR(20) DEFAULT NULL COMMENT '收货固定电话',
  `receiver_mobile` VARCHAR(20) DEFAULT NULL COMMENT '收货移动电话',
  `receiver_province` VARCHAR(20) DEFAULT NULL COMMENT '省份',
  `receiver_city` VARCHAR(20) DEFAULT NULL COMMENT '城市',
  `receiver_district` VARCHAR(20) DEFAULT NULL COMMENT '区/县',
  `receiver_address` VARCHAR(200) DEFAULT NULL COMMENT '详细地址',
  `receiver_zip` VARCHAR(6) DEFAULT NULL COMMENT '邮编',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;


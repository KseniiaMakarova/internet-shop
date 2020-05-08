CREATE SCHEMA `internet_shop` DEFAULT CHARACTER SET utf8 ;
CREATE TABLE `internet_shop`.`products` (
  `product_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(225) NOT NULL,
  `price` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`product_id`)
  );
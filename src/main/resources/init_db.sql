CREATE SCHEMA `internet_shop` DEFAULT CHARACTER SET utf8;
USE `internet_shop`;
CREATE TABLE `products`
(
    `product_id` BIGINT(11)     NOT NULL AUTO_INCREMENT,
    `name`       VARCHAR(225)   NOT NULL,
    `price`      DECIMAL(10, 2) NOT NULL,
    `available`  TINYINT        NOT NULL,
    PRIMARY KEY (`product_id`)
);
CREATE TABLE `users`
(
    `user_id`  BIGINT(11)     NOT NULL AUTO_INCREMENT,
    `name`     VARCHAR(225)   NOT NULL,
    `login`    VARCHAR(225)   NOT NULL,
    `password` VARCHAR(225)   NOT NULL,
    `salt`     VARBINARY(500) NOT NULL,
    PRIMARY KEY (`user_id`),
    UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,
    UNIQUE INDEX `salt_UNIQUE` (`salt` ASC) VISIBLE
);
CREATE TABLE `roles`
(
    `role_id`   BIGINT(11)   NOT NULL AUTO_INCREMENT,
    `role_name` VARCHAR(225) NOT NULL,
    PRIMARY KEY (`role_id`),
    UNIQUE INDEX `role_name_UNIQUE` (`role_name` ASC) VISIBLE
);
CREATE TABLE `orders`
(
    `order_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
    `user_id`  BIGINT(11) NOT NULL,
    PRIMARY KEY (`order_id`),
    INDEX `orders_users_fk_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `orders_users_fk`
        FOREIGN KEY (`user_id`)
            REFERENCES `internet_shop`.`users` (`user_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);
CREATE TABLE `carts`
(
    `cart_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT(11) NOT NULL,
    PRIMARY KEY (`cart_id`),
    UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) VISIBLE,
    CONSTRAINT `carts_users_fk`
        FOREIGN KEY (`user_id`)
            REFERENCES `internet_shop`.`users` (`user_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);
CREATE TABLE `orders_products`
(
    `order_id`   BIGINT(11) NOT NULL,
    `product_id` BIGINT(11) NOT NULL,
    INDEX `orders_products_orders_fk_idx` (`order_id` ASC) VISIBLE,
    INDEX `orders_products_products_fk_idx` (`product_id` ASC) VISIBLE,
    CONSTRAINT `orders_products_orders_fk`
        FOREIGN KEY (`order_id`)
            REFERENCES `internet_shop`.`orders` (`order_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `orders_products_products_fk`
        FOREIGN KEY (`product_id`)
            REFERENCES `internet_shop`.`products` (`product_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);
CREATE TABLE `carts_products`
(
    `cart_id`    BIGINT(11) NOT NULL,
    `product_id` BIGINT(11) NOT NULL,
    INDEX `carts_products_carts_fk_idx` (`cart_id` ASC) VISIBLE,
    INDEX `carts_products_products_fk_idx` (`product_id` ASC) VISIBLE,
    CONSTRAINT `carts_products_carts_fk`
        FOREIGN KEY (`cart_id`)
            REFERENCES `internet_shop`.`carts` (`cart_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `carts_products_products_fk`
        FOREIGN KEY (`product_id`)
            REFERENCES `internet_shop`.`products` (`product_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);
CREATE TABLE `users_roles`
(
    `user_id` BIGINT(11) NOT NULL,
    `role_id` BIGINT(11) NOT NULL,
    INDEX `users_roles_users_fk_idx` (`user_id` ASC) VISIBLE,
    INDEX `users_roles_roles_fk_idx` (`role_id` ASC) VISIBLE,
    CONSTRAINT `users_roles_users_fk`
        FOREIGN KEY (`user_id`)
            REFERENCES `internet_shop`.`users` (`user_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `users_roles_roles_fk`
        FOREIGN KEY (`role_id`)
            REFERENCES `internet_shop`.`roles` (`role_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);
INSERT INTO `internet_shop`.`roles` (`role_name`)
VALUES ('USER'),
       ('ADMIN');

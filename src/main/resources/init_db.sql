CREATE TABLE `bucket` (
  `bucket_id` int NOT NULL AUTO_INCREMENT,
  `user_bucket_id` int NOT NULL,
  PRIMARY KEY (`bucket_id`),
  KEY `fk_user_idx` (`user_bucket_id`),
  CONSTRAINT `fk_buket_user` FOREIGN KEY (`user_bucket_id`) REFERENCES `users` (`users_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8

CREATE TABLE `bucket_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `bucket_id` int NOT NULL,
  `item_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `bucket_items_items_fk_idx` (`item_id`),
  KEY `bucket_items_bucket_fk_idx` (`bucket_id`),
  CONSTRAINT `bucket_items_bucket_fk` FOREIGN KEY (`bucket_id`) REFERENCES `bucket` (`bucket_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `bucket_items_items_fk` FOREIGN KEY (`item_id`) REFERENCES `items` (`item_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8

CREATE TABLE `items` (
  `item_id` int NOT NULL AUTO_INCREMENT,
  `item_name` varchar(45) NOT NULL,
  `item_price` decimal(6,2) NOT NULL,
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8

CREATE TABLE `orders` (
  `orders_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  PRIMARY KEY (`orders_id`),
  KEY `orders_users_fk_idx` (`user_id`),
  CONSTRAINT `orders_users_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`users_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8

CREATE TABLE `orders_items` (
  `order_items_id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `item_id` int NOT NULL,
  PRIMARY KEY (`order_items_id`),
  KEY `order_items_order_fk_idx` (`order_id`),
  KEY `order_items_items_fk_idx` (`item_id`),
  CONSTRAINT `order_items_items_fk` FOREIGN KEY (`item_id`) REFERENCES `items` (`item_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_items_order_fk` FOREIGN KEY (`order_id`) REFERENCES `orders` (`orders_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8

CREATE TABLE `role` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8

CREATE TABLE `user_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_idx` (`user_id`) /*!80000 INVISIBLE */,
  KEY `fk_role_idx` (`role_id`),
  CONSTRAINT `fk_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`users_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8

CREATE TABLE `users` (
  `users_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `surname` varchar(45) DEFAULT NULL,
  `login` varchar(45) NOT NULL,
  `password` varchar(500) NOT NULL,
  `token` varchar(45) DEFAULT NULL,
  `salt` varbinary(200) DEFAULT NULL,
  PRIMARY KEY (`users_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8
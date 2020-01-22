CREATE SCHEMA `internetshop` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `internetshop`.`items` (
  `item_id` INT NOT NULL AUTO_INCREMENT,
  `item_name` VARCHAR(255) NOT NULL,
  `item_price` DECIMAL(6,2) NOT NULL,
  PRIMARY KEY (`item_id`));

INSERT INTO `internetshop`.`items` (`item_name`, `item_price`) VALUES ('xiaomi mi8', '1000');
SELECT * FROM internetshop.items where item_id=1;
INSERT INTO `internetshop`.`items` (`item_name`, `item_price`) VALUES ('iphone', '1000');
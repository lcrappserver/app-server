CREATE TABLE `tu_back_user_info` (
  `uuid` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(45) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `mobile` varchar(45) DEFAULT NULL,
  `real_name` varchar(45) DEFAULT NULL,
  `icon` varchar(2000) DEFAULT NULL,
  `sex` varchar(45) DEFAULT NULL,
  `degree` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `idx_tu_user_info_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

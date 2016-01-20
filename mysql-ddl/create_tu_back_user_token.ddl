CREATE TABLE `tu_back_user_token` (
  `uuid` int(11) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(45) NOT NULL,
  `password` varchar(45) DEFAULT NULL,
  `access_token` varchar(64) DEFAULT NULL,
  `user_id` varchar(45) NOT NULL,
  PRIMARY KEY (`uuid`),
  KEY `idx_tu_user_token_login_name` (`login_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

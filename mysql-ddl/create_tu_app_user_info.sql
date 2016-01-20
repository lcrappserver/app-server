CREATE TABLE `tu_app_user_info` (
  `uuid` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(45) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `mobile` varchar(45) DEFAULT NULL,
  `icon` varchar(45) DEFAULT NULL,
  `sex` varchar(45) DEFAULT NULL,
  `degree` varchar(45) DEFAULT NULL,
  `login_name` varchar(45) DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

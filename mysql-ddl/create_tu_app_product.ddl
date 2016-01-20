CREATE TABLE `tu_app_product` (
  `uuid` int(11) NOT NULL AUTO_INCREMENT,
  `prod_type` varchar(45) DEFAULT NULL COMMENT '类型：\n1. 油滴',
  `prod_brand` varchar(45) DEFAULT NULL COMMENT '品牌',
  `prod_name` varchar(45) DEFAULT NULL COMMENT '商品名称',
  `prod_id` varchar(45) NOT NULL COMMENT '商品id',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=120 DEFAULT CHARSET=utf8;

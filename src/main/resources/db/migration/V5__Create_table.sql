CREATE TABLE `beer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `brewery_id` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `cat_id` int(11) DEFAULT NULL,
  `style_id` int(11) DEFAULT NULL,
  `abv` double DEFAULT NULL,
  `ibu` double DEFAULT NULL,
  `srm` double DEFAULT NULL,
  `upc` int(11) DEFAULT NULL,
  `filepath` varchar(255) DEFAULT NULL,
  `descript` text,
  `add_user` int(11) DEFAULT NULL,
  `last_mod` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
)

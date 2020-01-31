CREATE TABLE `brewery` (
  `id` INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` varchar(255),
  `address1` varchar(255),
  `address2` varchar(255),
  `city` varchar(255),
  `state` varchar(255),
  `code` varchar(255),
  `country` varchar(255),
  `phone` varchar(255),
  `website` varchar(255),
  `filepath` varchar(255),
  `descript` text,
  `add_user` int(1),
  `last_mod` varchar(255)
)

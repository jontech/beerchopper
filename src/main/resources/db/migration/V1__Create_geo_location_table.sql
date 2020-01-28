CREATE TABLE `geo_location` (
  `id` int(11) DEFAULT NULL,
  `brewery_id` int(11) DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `accuracy` char(100) DEFAULT NULL
)

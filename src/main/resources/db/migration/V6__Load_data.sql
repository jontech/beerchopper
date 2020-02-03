LOAD DATA
     INFILE '/opt/mysql/data/beers.csv'
     INTO TABLE beer
     FIELDS TERMINATED BY ',' ENCLOSED BY '"'
     IGNORE 1 ROWS

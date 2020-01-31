LOAD DATA
    INFILE '/opt/mysql/data/breweries.csv'
    INTO TABLE brewery
    FIELDS TERMINATED BY ',' ENCLOSED BY '"'
    IGNORE 1 ROWS

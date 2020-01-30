LOAD DATA
    INFILE '/opt/mysql/data/geocodes.csv'
    INTO TABLE geo_location
    FIELDS TERMINATED BY ','
    IGNORE 1 ROWS

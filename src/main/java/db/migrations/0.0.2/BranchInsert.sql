TRUNCATE TABLE Branch;

LOAD DATA LOCAL INFILE 'data/Branch.csv' 
INTO TABLE Branch
CHARACTER SET UTF8
FIELDS TERMINATED BY ',' 
OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 1 ROWS
(@id,@firm_id,@locality_id,@address,@latitude,@longitude)
SET id=@id,firm_id=@firm_id,locality_id=@locality_id,address=@address,latitude=@latitude,longitude=@longitude,version=0, admin_id=1;
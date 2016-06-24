TRUNCATE TABLE Company;

LOAD DATA LOCAL INFILE 'data/Company.csv' 
INTO TABLE Company
CHARACTER SET UTF8
FIELDS TERMINATED BY ',' 
OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 1 ROWS
(@id,@cuit,@name)
SET id=@id,cuit=@cuit,name=@name,version=0;
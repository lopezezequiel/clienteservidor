TRUNCATE TABLE Locality;

LOAD DATA LOCAL INFILE 'data/Locality.csv' 
INTO TABLE Locality
CHARACTER SET UTF8
FIELDS TERMINATED BY ',' 
OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 1 ROWS
(@id,@department_id,@name)
SET id=@id, name=@name, department_id=@department_id, version=0;

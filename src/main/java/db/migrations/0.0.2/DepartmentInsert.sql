TRUNCATE TABLE Department;

LOAD DATA LOCAL INFILE 'data/Department.csv' 
INTO TABLE Department
CHARACTER SET UTF8
FIELDS TERMINATED BY ',' 
OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 1 ROWS
(@id,@province_id,@name)
SET id=@id, name=@name, province_id=@province_id, version=0;

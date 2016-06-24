TRUNCATE TABLE Firm;

LOAD DATA LOCAL INFILE 'data/Firm.csv' 
INTO TABLE Firm
CHARACTER SET UTF8
FIELDS TERMINATED BY ',' 
OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 1 ROWS
(@id,@company_id,@name)
SET id=@id,company_id=@company_id,name=@name,version=0;
TRUNCATE TABLE Unit;

LOAD DATA LOCAL INFILE 'data/Unit.csv' 
INTO TABLE Unit
FIELDS TERMINATED BY ',' 
ENCLOSED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 1 ROWS
(@id, @symbol, @singular, @plural)
SET id=@id, symbol=@symbol, singular=@singular, plural=@plural, version=0;
TRUNCATE TABLE Product;

LOAD DATA LOCAL INFILE 'data/Product.csv' 
INTO TABLE Product
CHARACTER SET UTF8
FIELDS TERMINATED BY ',' 
OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 1 ROWS
(@id,@ean13,@description,@unit_id,@number,@brand)
SET id=@id, ean13=@ean13, description=@description, unit_id=@unit_id, number=@number, brand=@brand, category_id=NULL, version=0;

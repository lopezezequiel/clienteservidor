TRUNCATE TABLE Category;

LOAD DATA LOCAL INFILE 'data/Category.csv' 
INTO TABLE Category
FIELDS TERMINATED BY ',' 
ENCLOSED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 1 ROWS
(@id, @name, @parent_id, @image)
SET id=@id, name=@name, parent_id=@parent_id, image=@image, version=0;
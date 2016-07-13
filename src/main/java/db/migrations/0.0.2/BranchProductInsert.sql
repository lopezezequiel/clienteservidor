TRUNCATE TABLE BranchProduct;

LOAD DATA LOCAL INFILE 'data/BranchProduct.csv' 
INTO TABLE BranchProduct
CHARACTER SET UTF8
FIELDS TERMINATED BY ',' 
OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 1 ROWS
(@price,@branch_id,@product_id)
SET price=@price,branch_id=@branch_id,product_id=@product_id,version=0;
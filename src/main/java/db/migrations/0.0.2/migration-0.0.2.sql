USE preciosclaros;

-- Previous migrations
SOURCE ../0.0.1/migration-0.0.1.sql

SET FOREIGN_KEY_CHECKS = 0;

-- Insert
SOURCE ../0.0.2/CategoryInsert.sql
SOURCE ../0.0.2/ProvinceInsert.sql
SOURCE ../0.0.2/UnitInsert.sql
SOURCE ../0.0.2/ProductInsert.sql
SOURCE ../0.0.2/LocalityInsert.sql
SOURCE ../0.0.2/DepartmentInsert.sql
SOURCE ../0.0.2/BranchInsert.sql
SOURCE ../0.0.2/FirmInsert.sql
SOURCE ../0.0.2/CompanyInsert.sql
SOURCE ../0.0.2/AfipInsert.sql

SET FOREIGN_KEY_CHECKS = 1;
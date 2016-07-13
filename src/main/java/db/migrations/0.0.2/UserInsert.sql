TRUNCATE TABLE User;
TRUNCATE TABLE UserRole;

INSERT INTO User(id, version, enabled, mail, name, password, hashCode) VALUES(1, 0, 1, 'adminc1@preciosclaros.com', 'Admin c1', '$2a$06$kRmNYt9Ullims0ylqbMd7u6kKdyDlFmcu/ThLATMc/R9uJj9UiPnO', null);
INSERT INTO User(id, version, enabled, mail, name, password, hashCode) VALUES(2, 0, 1, 'adminc2@preciosclaros.com', 'Admin c2', '$2a$06$0EaUf8bilmwdVyA2nz7oMOn0bkFCsG0jyqovfSuKxDzewCMwQP/5y', null);
INSERT INTO User(id, version, enabled, mail, name, password, hashCode) VALUES(3, 0, 1, 'basic@preciosclaros.com', 'Basic', '$2a$06$rkCOQC8BfAcR7mRiAeh0Fuf4nBBqkoZ2oBZdmlg8J5RQvEaJ5HuiC', null);
INSERT INTO User(id, version, enabled, mail, name, password, hashCode) VALUES(4, 0, 1, 'branch@preciosclaros.com', 'Branch', '$2a$06$v5jLpu6WxY/8SqcDf9li5OiYYWRX.lst064tzVEo6yGckAYOW/vka', null);

INSERT INTO UserRole(user_id, role) VALUES(1, 'ROLE_ADMIN_C1');
INSERT INTO UserRole(user_id, role) VALUES(2, 'ROLE_ADMIN_C2');
INSERT INTO UserRole(user_id, role) VALUES(3, 'ROLE_BASIC');
INSERT INTO UserRole(user_id, role) VALUES(4, 'ROLE_BRANCH');



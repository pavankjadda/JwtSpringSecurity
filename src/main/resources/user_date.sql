/* Insert Data into User Table  */
INSERT INTO user VALUES(1,'admin','2019-01-08 17:41:58','admin','2019-01-08 17:41:58',1,1,1,1,'$2y$12$ALU6qByNCKqkaTyjdLyqyOe7nQ0mGwQY3gXqBQ3DQKdfS4Yqwd7Jq','admin');
INSERT INTO user VALUES(2,'admin','2019-01-08 17:41:58','admin','2019-01-08 17:41:58',1,1,1,1,'$2a$12$JZipNtfy6B/wa9AvbrPq6.o9yi9TYR7CK.j3XG6BY3P3DwuFg7pWe','user');


/* Insert Data into Role Table  */
insert into role values(1,'ROLE_USER');
insert into role values(2,'ROLE_ADMIN');
insert into role values(3,'ROLE_APIUSER');
insert into role values(4,'ROLE_DBA');
insert into role values(5,'ROLE_SELLER');
insert into role values(6,'ROLE_BUYER');

/* Insert Data into UserRole Table  */
INSERT INTO user_role(user_id,role_id) VALUES (2,1);
INSERT INTO user_role(user_id,role_id) VALUES (1,2);
INSERT INTO user_role(user_id,role_id) VALUES (1,1);

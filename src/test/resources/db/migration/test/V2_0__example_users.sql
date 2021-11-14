INSERT INTO users (user_name, password, create_time) values ('admin', '$2a$10$yFDwc5f1b.regd3b..0r0OzfLtd9ugXKw0fPePrO3wyGIo2vAP9Yq', now());

INSERT INTO users_and_roles (users_id, roles) values ((select id from users where user_name = 'admin'), 'ROLE_ADMIN');

INSERT INTO users (user_name, password, create_time) values ('user', '$2a$10$UBExrId2vQtOXbFKZZXU9ubVUcIF2/2CdwFsNgVS3JBzqWAn.Xt0q', now());

INSERT INTO users_and_roles (users_id, roles) values ((select id from users where user_name = 'user'), 'ROLE_USER');

INSERT INTO users (user_name, password, create_time) values ('user2', '$2a$10$UBExrId2vQtOXbFKZZXU9ubVUcIF2/2CdwFsNgVS3JBzqWAn.Xt0q', now());

INSERT INTO users_and_roles (users_id, roles) values ((select id from users where user_name = 'user2'), 'ROLE_USER');

INSERT INTO users (user_name, password, create_time) values ('user3', '$2a$10$UBExrId2vQtOXbFKZZXU9ubVUcIF2/2CdwFsNgVS3JBzqWAn.Xt0q', now());

INSERT INTO users_and_roles (users_id, roles) values ((select id from users where user_name = 'user3'), 'ROLE_USER');
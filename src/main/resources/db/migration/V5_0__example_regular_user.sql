INSERT INTO users (user_name, password, create_time) values ('user', '$2a$10$UBExrId2vQtOXbFKZZXU9ubVUcIF2/2CdwFsNgVS3JBzqWAn.Xt0q', now());

INSERT INTO users_and_roles (users_id, roles) values ((select id from users where user_name = 'user'), 'ROLE_USER');
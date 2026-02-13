-- select id, username, password, authorities from users;
--insert into users (username, password, authorities) values ('user', 'user', 'USER'), ('admin', 'admin', 'ADMIN');
-- update users set authorities='admin' where username='admin';
-- delete from users;
insert into users (username, password, authorities) values ('ignore_this_user', 'create_with_controller_instead', 'USER');


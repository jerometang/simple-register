mysql -u root;
use mysql;

create user 'bm'@'%' identified by 'admin';
create database fogdev_club;

GRANT ALL ON fogdev_club.* TO 'bm'@'%';
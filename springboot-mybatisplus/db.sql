drop database if exists springboot_demo; -- 数据库名不能用中划线-
create database springboot_demo charset utf8;
use springboot_demo;
create table `admin`(
  id bigint auto_increment,
  loginName varchar(255),
  password varchar(64),
  lastLoginTime datetime,
  remark  varchar(255),
  primary key (id)
);
insert into admin(loginName,password,remark) values ('admin','admin','测试数据:管理员用户');

create table `book`(
  id bigint auto_increment,
  name varchar(255),
  price double,
  author varchar(255),
  creatorId int ,
  remark  varchar(255),
  primary key (id)
);

# 分类表
CREATE TABLE category (
cid VARCHAR(32) PRIMARY KEY ,
cname VARCHAR(50)
);
#商品表
CREATE TABLE products(
pid VARCHAR(32) PRIMARY KEY ,
pname VARCHAR(50),
price INT,
flag VARCHAR(2),
category_id VARCHAR(32),
CONSTRAINT products_fk FOREIGN KEY (category_id) REFERENCES category (cid)
);


INSERT INTO category(cid,cname) VALUES('c001','家电');
INSERT INTO category(cid,cname) VALUES('c002','服饰');
INSERT INTO category(cid,cname) VALUES('c003','化妆品');
#商品
INSERT INTO products(pid, pname,price,flag,category_id) VALUES('p001','联想',5000,'1','c001');
INSERT INTO products(pid, pname,price,flag,category_id) VALUES('p002','海尔',3000,'1','c001');
INSERT INTO products(pid, pname,price,flag,category_id) VALUES('p003','雷神',5000,'1','c001');
INSERT INTO products (pid, pname,price,flag,category_id) VALUES('p004','JACKJONES',800,'1','c002');
INSERT INTO products (pid, pname,price,flag,category_id) VALUES('p005','真维斯',200,'1','c002');
INSERT INTO products (pid, pname,price,flag,category_id) VALUES('p006','花花公子',440,'1','c002');
INSERT INTO products (pid, pname,price,flag,category_id) VALUES('p007','劲霸',2000,'1','c002');
INSERT INTO products (pid, pname,price,flag,category_id) VALUES('p008','香奈儿',800,'1','c003');
INSERT INTO products (pid, pname,price,flag,category_id) VALUES('p009','相宜本草',200,'1','c003');

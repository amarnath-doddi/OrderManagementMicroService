--create schema fund_management;
CREATE SEQUENCE hibernate_sequence START WITH 1 INCREMENT BY 1 NOCYCLE;
create table orders(id number primary key,user_id number, total number(5,2), order_date date);
create table order_line(id number primary key, order_id number, product_id number,quantity number,price number(5,2), foreign key(order_id) references orders(id));

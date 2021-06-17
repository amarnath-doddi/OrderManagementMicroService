insert into orders(id,user_id,total,order_date) values(1000,1000,902,sysdate);
insert into orders(id,user_id,total,order_date) values(1001,1002,331,sysdate);


insert into order_line(id,order_id,product_id,quantity,price) values(2001,1000,2001,5,500.00);
insert into order_line(id,order_id,product_id,quantity,price) values(2002,1000,2010,2,402.00);
insert into order_line(id,order_id,product_id,quantity,price) values(2003,1001,2005,1,55.00);
insert into order_line(id,order_id,product_id,quantity,price) values(2004,1001,2006,1,54.00);
insert into order_line(id,order_id,product_id,quantity,price) values(2005,1001,2007,1,12.00);
insert into order_line(id,order_id,product_id,quantity,price) values(2006,1001,2008,1,65.00);
insert into order_line(id,order_id,product_id,quantity,price) values(2007,1001,2009,1,145.00);

/*jdbc homewot 第3題 改寫employee表格 新增一個可以放照片的欄位*/
drop table employee_p


CREATE TABLE employee_p (
	empno		decimal(4)		PRIMARY KEY,	
	ename		varchar(30)		NOT NULL,
	hiredate	datetime		NOT NULL,
	salary		decimal(10,2)	NOT NULL,
	deptno		decimal(3)		NOT NULL,
	title		varchar(30)		NOT NULL,
/*	photo       varbinary(max)  not null,*/
	FOREIGN KEY (deptno) REFERENCES department (deptno)
	);

INSERT INTO employee_p VALUES (1001,'Pam Pan', '2010/11/10', 56000, 100, 'senior engineer');
INSERT INTO employee_p VALUES (1002,'Lily Lee', '2008/03/22', 34000, 100, 'engineer');
INSERT INTO employee_p VALUES (1003,'Stephen Hsu', '2006/08/14', 77000, 200, 'manager');
INSERT INTO employee_p VALUES (1004,'May Wu', '2011/04/04', 67000, 300, 'manager');
INSERT INTO employee_p VALUES (1005,'Tina Wang', '2013/12/25', 37000, 200, 'engineer');
INSERT INTO employee_p VALUES (1006,'Allen Hu', '2007/07/06', 44000, 300, 'senior engineer');
INSERT INTO employee_p VALUES (1007,'David Ho', '2009/09/11', 39000, 100, 'engineer');
INSERT INTO employee_p VALUES (1008,'Viginia Kuo', '2000/05/16', 55000, 100, 'engineer');



alter table employee_p add photo varbinary(max) 

/*
INSERT INTO employee_p (empno,photo) VALUES (8888, 01321333);
update employee_p set photo =0000 where empno =1008
*/
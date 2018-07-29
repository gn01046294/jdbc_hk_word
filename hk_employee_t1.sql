/* jdbc homework 第2題 從txt檔讀取後新增到table內 



CREATE TABLE employee_t1 (
	empno		decimal(4)		PRIMARY KEY,
	ename		varchar(30)		NOT NULL,
	hiredate	datetime		NOT NULL,
	salary		decimal(10,2)	NOT NULL,
	deptno		decimal(3)		NOT NULL,
	title		varchar(30)		NOT NULL,
	FOREIGN KEY (deptno) REFERENCES department (deptno)
);
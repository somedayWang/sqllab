create table PRODUCT
(
	PID int primary key,
	P_NAME char(30),
	UNIT char(10),
	P_PRICE int,
	REMAINING int,
);
create table TRADE
(
	T_DATE DATE,
	T_TIME TIME,
	T_PRICE int,
	PID int,
	P_NAME char(30),
	CID int,
	foreign key (PID) references PRODUCT(PID)
);
create table VIP
(
	VID int,
	T_NUM int,
	OVERTAG char,
	PAST_DATE DATE,
	PAST_TIME TIME
);
create table CASHIER
(
	CID int primary key,
	C_NAME char(30),
	C_PW char(30),
	C_PHONE char(20)
);
create table CLOGS
(
	PID int,
	CID int,
	P_PRICE int,
	P_NUM int,
	W_DATE DATE,
	W_TIME TIME,
	foreign key (CID) references CASHIER(CID),
	foreign key (PID) references PRODUCT(PID)
);
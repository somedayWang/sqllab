create table FILM
(	FID int primary key,
	FNAME char(30), 
	FTYPE char(10), 
	DNAME char(30), 
	length int, 
	IS3D char(1),
	GRADE int
);
create table ACTOR
(	
	ACTID int primary key, 
	ANAME char(30),
	SEX char(2), 
	BYEAR int
);
create table ACTIN
(
	ACTID int, 
	FID int, 
	primary key(ACTID,FID),
	ISLEADING char(1), 
	GRADE int,
	foreign key (ACTID) references ACTOR(ACTID),
	foreign key (FID) references FILM(FID)
);
create table THEATER 
(
	TID int primary key, 
	TNAME char(20), 
	TAREA char(20), 
	ADDRESS char(30)
);
create table SHOW
(
	FID int, 
	TID int, 
	primary key(FID,TID),
	PRICE int, 
	YEAR int , 
	MONTH int,
	foreign key (FID) references FILM(FID),
	foreign key (TID) references THEATER(TID)
);
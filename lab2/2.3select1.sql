-- 2.3.1
select * from SHOW,THEATER
where THEATER.TID=(select TID from THEATER where TAREA='洪山区') and FID=(select FID from FILM where FNAME='战狼1') and YEAR=2017
order by MONTH desc;

-- 2.3.2
select * from FILM
where FILM.FID not in
(select ACTIN.FID from ACTIN)
order by FTYPE,GRADE desc;

-- 2.3.3
select FID,FNAME,DNAME from FILM
where FILM.FID not in
(
	select SHOW.FID from SHOW
	where YEAR<=2017
);

-- 2.3.4
select FID from FILM
where not exists 
(
	select * from THEATER where not exists 
	(
		select * from SHOW
		where FID=FILM.FID and TID=THEATER.TID
	)
);

-- 2.3.5
select FID,FNAME,DNAME,GRADE from FILM
where GRADE not between 80 and 89;

-- 2.3.6
select DNAME,max(GRADE),min(GRADE) from FILM
group by DNAME;

-- 2.3.7
select DNAME,count(FID) from FILM
group by DNAME
having count(FID)>=2;

--2.3.8
select DNAME,count(*),avg(GRADE) from FILM
where exists
(
	select DNAME from FILM 
	where GRADE>=80
	group by DNAME
	having count(*)>=2
)
group by DNAME;

--2.3.9
select distinct DNAME,ACTOR.ACTID,ANAME from FILM,ACTIN,ACTOR
where FILM.FID=ACTIN.FID and ACTIN.ACTID=ACTOR.ACTID and DNAME in
(
	select DNAME from FILM 
	group by DNAME
	having count(FID)>=2
);

--2.3.10
select ACTIN.ACTID,ANAME,avg(GRADE) from ACTOR,ACTIN
where ISLEADING ='Y' and ACTIN.ACTID=ACTOR.ACTID
group by ACTIN.ACTID,ANAME;

--2.3.11
select FNAME, min(YEAR*12+MONTH-1)/12 as YEAR ,min(YEAR*12+MONTH-1)%12 +1 as MONTH from FILM,SHOW
where GRADE>90 and FILM.FID=SHOW.FID
group by FNAME;

select  min(YEAR*12+MONTH-1)/12 as YEAR ,min(YEAR*12+MONTH-1)%12 +1 as MONTH from FILM,SHOW
where GRADE>90 and FILM.FID=SHOW.FID;

select FNAME,YEAR,MONTH from FILM,SHOW
where GRADE>90 and FILM.FID=SHOW.FID 
and YEAR*12+MONTH-1 in
(
select min(YEAR*12+MONTH-1) from FILM,SHOW
where GRADE>90 and FILM.FID=SHOW.FID
);

--2.3.12
select FNAME,TID,YEAR,MONTH from FILM,SHOW
where GRADE>90 and FILM.FID=SHOW.FID 
and YEAR*12+MONTH-1 in
(
select min(YEAR*12+MONTH-1) from FILM,SHOW
where GRADE>90 and FILM.FID=SHOW.FID
);

--2.3.13
select FID,count(*) from SHOW
group by FID;

--2.3.14
select DNAME from FILM
where FTYPE in ( '动作片', '警匪片', '枪战片');

--2.3.15
select FILM.FID,FNAME,TNAME,YEAR,MONTH from FILM,SHOW,THEATER
where FILM.FID=SHOW.FID and SHOW.TID=THEATER.TID 
and FNAME like '战狼%'
order by FNAME;

--2.3.16
select first.TID from SHOW first,SHOW second
where first.TID=second.TID and first.YEAR=second.YEAR and first.MONTH=second.MONTH and first.FID=1 and second.FID=2;

--2.3.17
select ACTID,ANAME 
from ACTOR
where ACTID not in
(
	select ACTID from ACTIN left join FILM on ACTIN.FID=FILM.FID
	where FILM.GRADE < 85
);

--2.3.18
select ANAME 
from ACTOR
where ACTID  in
(
	select ACTID from ACTIN left join FILM on ACTIN.FID=FILM.FID
	where DNAME='吴宇森'
);

--2.3.19
select ACTID,ANAME,FNAME
from ACTOR left join ACTIN on ACTOR.ACTID=ACTIN.ACTID left join FILM on ACTIN.FID=FILM.FID;

--2.3.20
select FID,FNAME
from FILM
where GRADE is NULL and FID in
(	
	select FID from SHOW
	group by FID
	having count(*)>=3
);
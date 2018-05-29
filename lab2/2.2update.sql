--2.2.1
insert into FILM(FID,FNAME,FTYPE,DNAME,length,IS3D,GRADE) values(1,2,3,4,5,6,7);
update  FILM set FNAME='xixi'  where FID=1;
delete from FILM where FID=1;
--2.2.2
select * into YOUNG_ACTOR from ACTOR where BYEAR >=1990;
--2.2.3


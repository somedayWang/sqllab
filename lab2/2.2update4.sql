USE [lab1]
GO
/****** Object:  Trigger [dbo].[zhouxingchi]    Script Date: 2018/5/15 9:25:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER trigger [dbo].[zhouxingchi]
 on [dbo].[FILM]
after  insert,update
as
begin
if exists(select * from inserted where DNAME ='周星驰')
 update FILM
 set FTYPE='喜剧片'
 where 
 FID=(select FID from inserted where DNAME ='周星驰')
end;
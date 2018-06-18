--delete dbo.CLOGS where CID=1122;
--login in
create or alter procedure loginca @id int ,@name char(30), @pw char(30), @ntime datetime 
as
	declare @rpw char(30)
	declare @num int
	select @num=COUNT(*) from CASHIER where CID=@id or C_NAME=@name group by CID
	if(@num=0)	return 1
	select @rpw=C_PW from CASHIER where CID=@id
	if(@rpw=@pw)
	begin
		update CASHIER set W_DATETIME=@ntime where CID=@id
		return @id
	end
	select @rpw=C_PW from CASHIER where C_NAME=@name
	if(@rpw=@pw)
	begin
		update CASHIER set W_DATETIME=@ntime where C_NAME=@name
		select @num=CID from CASHIER where C_NAME=@name
		return @num
	end
	return 2
go

create or alter procedure newca @name char(30) ,@pw char(30), @phone char(20), @yanzheng char(30)
as
	declare @id int
	declare @num int
	select @id=COUNT(*) from CASHIER where CID = 0 and C_PW = @yanzheng
	if(@id=1)
		begin
		select @id=COUNT(*) from CASHIER where C_NAME = @name
		if(@id=0)
		begin
			select @id=FLOOR(RAND()*9000)+1000 
			select @num=COUNT(*) from CASHIER where CID = @id 
			while(@num!=0)
			begin
			 select @id=FLOOR(RAND()*9000)+1000
			 select @num=COUNT(*) from CASHIER where CID = @id 
			end
			insert into CASHIER(CID,C_NAME,C_PW,C_PHONE)
			values(@id,@name,@pw,@phone)
			return @id
		end
		else
		begin
			select @id=CID from CASHIER where C_NAME = @name and C_PW=@pw
			set @id=@id-10000
			return @id
		end
	end
	else
	return 1
go

--insert ca
create or alter procedure insertca  @cid int, @name char(30) ,@pw char(30), @phone char(20)
as
	declare @id int
	select @id=COUNT(*) from CASHIER where CID = @cid or C_NAME=@name
	if(@id=0)
	begin
		insert into CASHIER(CID,C_NAME,C_PW,C_PHONE)
		values(@cid,@name,@pw,@phone)
		return 0
	end
	else
	begin
		return 1
	end
go

--insert pro
create or alter procedure insertpro  @pname char(30), @unit char(10), @price money,@remain int 
as
	declare @id int
	declare @num int
	begin
	select @id=COUNT(*) from PRODUCT where P_NAME = @pname and UNIT=@unit
	if(@id=0)
	begin
		select @id=FLOOR(RAND()*90000)+10000 
		select @num=COUNT(*) from CASHIER where CID = @id 
		while(@num!=0)
		begin
			select @id=FLOOR(RAND()*90000)+10000
			select @num=COUNT(*) from CASHIER where CID = @id 
		end
		insert into PRODUCT(PID,P_NAME,UNIT,P_PRICE,REMAINING)
		values(@id,@pname,@unit,@price,@remain)
		return @id
	end
	else
	begin
		select @id=PID from PRODUCT where P_NAME = @pname and UNIT=@unit
		set @id=@id-100000
		return @id
	end
end
go

--sell pro
create or alter procedure sellpro @id int ,@number int, @cashier int,@vip int,@ntime datetime 
as
	declare @remain int
	declare @pname char(30)
	declare @price money
	select @remain=REMAINING ,@pname= P_NAME, @price=P_PRICE from PRODUCT where PID=@id
	if(@remain>=@number)	
	begin
		update PRODUCT set REMAINING = @remain-@number where PID=@id
		if(@vip=1)  set @price=0.9*@price
		insert into TRADE(T_DATETIME,T_PRICE,PID,P_NAME,CID,T_NUM) 
		values (@ntime,@price,@id,@pname,@cashier,@number) 
		return 0;
	end
	else return 1;
go

--insert totalmoney 
create or alter procedure totalmoney  @price money, @cashier int,@ntime datetime 
as
	insert into TRADE(T_DATETIME,T_PRICE,PID,P_NAME,CID) 
		values (@ntime,@price,null,'单笔总计',@cashier) 
		return 0
go

--update vip

create or alter procedure upodatevip @total money,@vid int,@ntime datetime
as
	declare @overtag char
	declare @num money
	declare @bdate datetime
	declare @bbdate datetime
	select @overtag=OVERTAG,@num=T_NUM,@bdate=PAST_DATETIME from VIP where VID=@vid
	select @bbdate=DATEADD(year,-1,@bdate)
	--if(DATEDIFF('s',@bbdate,@ntime)>=0 and DATEDIFF('s',@ntime,@bdate)>=0  )
	--else @num=0
	if(@overtag=1) return 1
	if(@num>=1200)	set @num=@num+@total
	else 
	begin
		set @num=@num+@total
		if(@num>=1200)
		begin
			select @bdate=DATEADD(year,1,@bdate)
			update VIP set T_NUM=@num ,PAST_DATETIME=@bdate where VID=@vid
			return -1
		end
	end
	update VIP set T_NUM=@num ,PAST_DATETIME=@bdate where VID=@vid
	return 0
go

create or alter procedure ifvip @vid int,@ntime datetime
as
	declare @bdate datetime
	declare @bbdate datetime
	declare @num int
	select @num=COUNT(*) from VIP where VID=@vid
	if(@num=0)
	begin
		return 1
	end
	select @bdate=PAST_DATETIME from VIP where VID=@vid
	select @bbdate=DATEADD(year,-1,@bdate)
	if(DATEDIFF(second,@bbdate,@ntime)>=0 and DATEDIFF(second,@ntime,@bdate)>=0)
	begin
		update VIP set OVERTAG=0 where VID=@vid
		return 0
	end
	else 
	update VIP set OVERTAG=1,T_NUM=0 where VID=@vid
	return 2
	--update VIP set T_NUM=@num ,PAST_DATETIME=@bdate where VID=@vid
go


--newvip
create or alter procedure newvip @name char(20),@phone char(15),@ntime datetime,@cashier int,@money int
as
	declare @id int
	declare @num int
	declare @atime datetime
	select @id=COUNT(*) from VIP where V_NAME = @name and V_PHONE =@phone
	if(@id=0)
	begin
		select @id=FLOOR(RAND()*90000)+10000 
		select @num=COUNT(*) from VIP where VID = @id 
		while(@num!=0)
		begin
		 select @id=FLOOR(RAND()*90000)+10000
		 select @num=COUNT(*) from VIP where VID = @id 
		end
		select @atime=DATEADD(year,1,@ntime)
		insert into VIP(VID,V_NAME,T_NUM,V_PHONE,OVERTAG,PAST_DATETIME)
		values(@id,@name,0,@phone,0,@atime)
		if(@money=0)
		insert into TRADE(T_DATETIME,T_PRICE,PID,P_NAME,CID,T_NUM) 
			values (@ntime,0,null,'办理会员',@cashier,1)
		else
		insert into TRADE(T_DATETIME,T_PRICE,PID,P_NAME,CID,T_NUM) 
			values (@ntime,50,null,'办理会员',@cashier,1)
		return @id
	end
	else
	begin
		select @id=VID from VIP where V_NAME = @name and V_PHONE =@phone 
		set @id=@id-100000
		return @id
	end
	
go
--revivevip
create or alter procedure revivevip @vid int, @ntime datetime, @cashier int,@money int
as
	declare @atime datetime
	declare @num int
	declare @over char
	select @num=COUNT(*) from VIP where VID=@vid
	select @over=OVERTAG from VIP where VID=@vid
	if(@num=1 and @over=1)
	begin
		select @atime=DATEADD(year,1,@ntime)
		update VIP set T_NUM=0,OVERTAG=0,PAST_DATETIME=@atime where VID=@vid
		if(@money=0)
		insert into TRADE(T_DATETIME,T_PRICE,PID,P_NAME,CID,T_NUM) 
			values (@ntime,0,null,'激活失效会员',@cashier,1)
		else
		insert into TRADE(T_DATETIME,T_PRICE,PID,P_NAME,CID,T_NUM) 
			values (@ntime,50,null,'激活失效会员',@cashier,1)
		return 0
	end
	else
	begin
		if(@over=0)
		return 1
		else
		return 2
	end
go
--about clogs
create or alter procedure xiabanle @cid int, @wtime datetime, @ltime datetime
as
	update CLOGS set L_DATETIME=@ltime where CID=@cid and @wtime=W_DATETIME
go



--trigger
create or alter trigger sellupclogs
on TRADE
after insert
as
	declare @wtime datetime
	declare @cid int
	declare @pid int
	declare @price money
	declare @bprice money
	declare @num int
	declare @bnum int
	declare @name char(30)
	declare @count int
	select @cid =CID,@pid=PID,@price=T_PRICE,@num=T_NUM,@name=P_NAME from inserted
	select @wtime=W_DATETIME from CASHIER where CID=@cid
	if(@name='单笔总计')
	begin
		select @count=COUNT(*) from CLOGS where W_DATETIME=@wtime and CID=@cid and @name=P_NAME
		if(@count=0)
		begin
			insert into CLOGS(PID,CID,P_PRICE,P_NUM,W_DATETIME,P_NAME)values(@pid,@cid,@price,1,@wtime,@name)
		end
		else
		begin
		select @bnum=P_NUM,@bprice=P_PRICE  from CLOGS where  W_DATETIME=@wtime and CID=@cid and @name=P_NAME
		update CLOGS set P_NUM=@bnum+1,P_PRICE=@bprice+@price where W_DATETIME=@wtime and CID=@cid and @name=P_NAME
		end
	end
	else
	begin
		select @count=COUNT(*) from CLOGS where W_DATETIME=@wtime and CID=@cid and PID=@pid and P_PRICE=@price and @name=P_NAME
		if(@count=0)
		insert into CLOGS(PID,CID,P_PRICE,P_NUM,W_DATETIME,P_NAME)values(@pid,@cid,@price,@num,@wtime,@name)
		else
		begin
			select @bnum=P_NUM from CLOGS where W_DATETIME=@wtime and CID=@cid and PID=@pid and P_PRICE=@price and @name=P_NAME
			update CLOGS set P_NUM=@bnum+@num where W_DATETIME=@wtime and CID=@cid and PID=@pid and P_PRICE=@price and @name=P_NAME
		end
	end

go
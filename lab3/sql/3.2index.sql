--index for product
create unique index INDEX_PID on PRODUCT(PID);
--index for vip
create unique index INDEX_VID on VIP(VID);
--index for cashier
create unique index INDEX_CID on CASHIER(CID);
--index for trade
create unique index INDEX_TID on TRADE(T_DATE,T_TIME,PID);



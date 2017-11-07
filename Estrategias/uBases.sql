
--Creacion usuario bases
create user bases identified by bases;

grant connect, resource, dba to bases;
grant unlimited tablespace to bases;
grant create session to bases;
grant create table to bases;
grant create view to bases;
grant create any trigger to bases;
grant create any procedure to bases;
grant create sequence to bases;
grant create synonym to bases;
grant create any table to bases;

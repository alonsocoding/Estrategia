-- CORRER ESTE SCRIPT ANTES --

-- Drop tables --

drop table Contiene;
drop table Estrategia;
drop table Periodo;
drop table Servidor;

-- Crear la tabla servidores --

create table Servidor (
    nombre_servidor varchar(20),
    nombre_base varchar(20),
    nombre_usuario varchar (20),
    contrasena varchar(20),
    ip varchar(20),
    puerto int,
    constraint pkServidor primary key (nombre_servidor)
);

create table Periodo (
    nombre_periodo varchar(20),
    lunes int,
    martes int,
    miercoles int,
    jueves int,
    viernes int,
    sabado int,
    domingo int,
    hora int, minuto int, segundo int,
    constraint pkPeriodo primary key (nombre_periodo)
);

create table Estrategia (
    nombre_estrategia varchar(20),
    tipo_respaldo varchar(20),
    modo_respaldo varchar(20),
    metodo_respaldo varchar(20),
    objetos varchar(100),
    nombre_periodo varchar(20),
    activo int,
    nombre_servidor varchar(20),
    constraint pkEstrategia primary key (nombre_estrategia),
    constraint fkEstrategia foreign key (nombre_periodo) references Periodo,
     constraint fkContiene foreign key (nombre_servidor) references Servidor
);




-- Pruebas --

insert into Servidor values('SE01', 'XE', 'U1', '123', '172.168.14.0', 3000);
insert into Servidor values('SE02', 'XE', 'U2', '456', '172.168.16.0', 4000);
insert into Servidor values('SE03', 'XE', 'U3', '789', '172.168.18.0', 5000);

insert into Periodo values('PE01',1,0,1,0,0,0,0,2,30,98);
insert into Periodo values('PE02',1,0,1,0,0,0,0,2,30,98);
insert into Periodo values('PE03',1,0,1,0,0,0,0,2,30,98);

insert into Estrategia values('ES01', 'Frio', 'Manual', 'Archive', 'USERS, TEMP','PE01',1,'SE01'); 
insert into Estrategia values('ES02', 'Frio', 'Manual', 'Archive', 'USERS, TEMP','PE02',1,'SE02'); 
insert into Estrategia values('ES03', 'Frio', 'Manual', 'Archive', 'USERS, TEMP','PE03',1,'SE03'); 
insert into Estrategia values('ES04', 'Frio', 'Manual', 'Archive', 'USERS, TEMP','PE03',1,'SE01'); 
-----------------------------------------------------------------------------------------------
--------------------------------------PROCEDIMIENTOS-------------------------------------------

--Procedimiento para eliminar un servidor
create or replace procedure e1(nombreServ varchar)
	is
	nombre varchar2(20);
	Cursor c1 is 
		select nombre_servidor from Servidor where nombre_servidor = nombreServ;
	begin
		open c1;
		fetch c1 into nombre;
        if c1%found then
            e2(nombre); --Para elimnar estrategia hay que usar el metodo de david, pero con el nombre _servidor
            delete from servidor where nombre_servidor = nombre;
        end if;
    end;
/
--Procedimiento para eliminar estrategia. (cuando se eliminar servidor)
create or replace procedure e2(nombreServ varchar)
	is
    nombreEst varchar2(20);
    Cursor c1 is 
		select nombre_estrategia from estrategia where nombre_servidor = nombreServ;
	begin
        open c1;
		fetch c1 into nombreEst;
		delete from estrategia where nombre_servidor = nombreServ;
        sp_dropJob(nombreEst); 
    end;
/
--Procedimiento para eliminar estrategia. (Eliminando solo la estrategia).
create or replace procedure e2(nombre varchar)
	is
	begin
		delete from estrategia where nombre_estrategia = nombre;
        sp_dropJob(nombre); 
    end;
/
--Procedmiento para elimnar la estrategia.
create or replace procedure sp_dropJob(name varchar)
is
begin 
    dbms_scheduler.drop_job ( 
        job_name    => name); 
end; 
/

-------------------------------------------------------------------------------------------------------

select Servidor.nombre_servidor, Estrategia.nombre_estrategia 
from Servidor, Estrategia, Contiene 
where Servidor.nombre_servidor = Contiene.nombre_servidor
and Estrategia.nombre_estrategia = Contiene.nombre_estrategia 
and Servidor.nombre_servidor = 'SE03';

-- Create database link --

CREATE PUBLIC DATABASE LINK DLBD1
CONNECT TO U1
IDENTIFIED BY "U1"
USING 'CBD1';

-- Create database link without tnsname.ora --

CREATE PUBLIC DATABASE LINK DLBD2
CONNECT TO system identified BY "root"
USING
'(DESCRIPTION =
    (ADDRESS = (PROTOCOL = TCP)(HOST = 172.17.28.149)(PORT = 1521))
    (CONNECT_DATA =
      (SERVER = DEDICATED)
      (SERVICE_NAME = XE)
      (SID = XE)
    )
  )'
/

CREATE DATABASE LINK dl1
CONNECT TO SYSTEM IDENTIFIED BY root
USING 'dl1';

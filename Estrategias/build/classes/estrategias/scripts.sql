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
    constraint pkEstrategia primary key (nombre_estrategia),
    constraint fkEstrategia foreign key (nombre_periodo) references Periodo
);

create table Contiene (
    nombre_servidor varchar(20),
    nombre_estrategia varchar(20),
    constraint pkContiene primary key (nombre_servidor, nombre_estrategia),
    constraint fkContiene foreign key (nombre_servidor) references Servidor,
    constraint fkContiene2 foreign key (nombre_estrategia) references Estrategia
);

-- Pruebas --

insert into Servidor values('SE01', 'XE', 'U1', '123', '172.168.14.0', 3000);
insert into Servidor values('SE02', 'XE', 'U2', '456', '172.168.16.0', 4000);
insert into Servidor values('SE03', 'XE', 'U3', '789', '172.168.18.0', 5000);

insert into Periodo values('PE01',1,0,1,0,0,0,0,2,30,98);
insert into Periodo values('PE02',1,0,1,0,0,0,0,2,30,98);
insert into Periodo values('PE03',1,0,1,0,0,0,0,2,30,98);

insert into Estrategia values('ES01', 'Frio', 'Manual', 'Archive', 'USERS, TEMP','PE01',1); 
insert into Estrategia values('ES02', 'Frio', 'Manual', 'Archive', 'USERS, TEMP','PE02',1); 
insert into Estrategia values('ES03', 'Frio', 'Manual', 'Archive', 'USERS, TEMP','PE03',1); 

insert into Contiene values('SE01','ES01');
insert into Contiene values('SE02','ES02');
insert into Contiene values('SE03','ES03');

-- Select de multiples tablas --

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

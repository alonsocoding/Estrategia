-- CORRER ESTE SCRIPT ANTES --

-- Drop tables --

drop table Evidencia;
drop table Estrategia;
drop table Periodo;
drop table Servidor;

-- Crear la tabla servidores --
create table Evidencia (
    nombre_estrategia varchar(20),
    run varchar(100),
    exitoso varchar(20),
    fecha_inicio varchar(20),
    fecha_final varchar(20)
);

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
 
 --Procedimiento para eliminar estrategia. (Eliminando solo la estrategia).
 create or replace procedure eliminarEstrategia(nombre varchar)
 	is
 	begin
 	delete from estrategia where nombre_estrategia = nombre;
        sp_dropJob(nombre);
     end;
 /
 --Procedmiento para elimnar el job de la estrategia.
 create or replace procedure sp_dropJob(estrategia varchar)
 is
 begin 
     dbms_scheduler.drop_job ( 
         job_name    => estrategia);
    commit;
 end; 
 /
 
 create or replace procedure sp_runJob(estrategia varchar)
 is
 begin 
     dbms_scheduler.run_job(job_name=> estrategia,USE_CURRENT_SESSION=>true);
    commit;
 end; 
 /
 
 -- Procedimiento para crear el job de la estrategia.
create or replace procedure sp_createJob(estrategia varchar, ruta varchar, frecuencia varchar, inicio timestamp)
is
begin    
    dbms_scheduler.create_job( 
        job_name        => estrategia, 
        job_type        => 'EXECUTABLE', 
        job_action      => ruta, 
        start_date      => inicio, 
        repeat_interval => frecuencia, 
        enabled         => true, 
        comments        => 'Backup database using RMAN');
	commit;
end;
/

 --Procedimiento para eliminar un servidor
 create or replace procedure eliminarServidor(nombreServ varchar)
 	is
 	nombre varchar2(20);
 	Cursor c1 is 
 		select nombre_servidor from Servidor where nombre_servidor = nombreServ;
 	begin
            open c1;
            fetch c1 into nombre;
            eliminarServidor2(nombre); --Para elimnar estrategia hay que usar el metodo de david, pero con el nombre _servidor
            delete from servidor where nombre_servidor = nombre;
     end;
 /
 --Procedimiento para eliminar estrategia. (cuando se eliminar servidor)
 create or replace procedure eliminarServidor2(nombreServ varchar)
 	is
     nombreEst varchar2(20);
     Cursor c1 is 
 		select nombre_estrategia from estrategia where nombre_servidor = nombreServ;
 	begin
        open c1;
 	fetch c1 into nombreEst;
        while c1 % found loop
 	delete from estrategia where nombre_servidor = nombreEst;
        sp_dropJob(nombreEst); 
        fetch c1 into nombreEst;
        end loop;
     end;
 /
 +
 +--Procedimiento que actualiza la frecuencia de la estrategia
 +create or replace procedure alterJob(estrategia varchar, frecuencia varchar)
 +is
 +BEGIN
 +DBMS_SCHEDULER.SET_ATTRIBUTE (estrategia,
 +   'repeat_interval', 'FREQ=WEEKLY;'+frecuencia);
 +commit;   
 +END;
 +/
 +
 +--Procedimiento para deshabilitar un job
 +create or replace procedure deshabilitarJob(estrategia varchar)
 +is
 +BEGIN 
 +  DBMS_SCHEDULER.DISABLE(estrategia);
 +  commit;
 +END;
 +/
 +
 +--Procedimiento para habilitar un job
 +create or replace procedure habilitarJob(estrategia varchar)
 +is
 +BEGIN 
 +  DBMS_SCHEDULER.ENABLE(estrategia);
 +  commit;
 +END;
 +/
 +
 +-------------------------------------------------------------------------------------------------------
 +
 +select Servidor.nombre_servidor, Estrategia.nombre_estrategia 
 +from Servidor, Estrategia, Contiene 
 +where Servidor.nombre_servidor = Contiene.nombre_servidor
 +and Estrategia.nombre_estrategia = Contiene.nombre_estrategia 
 +and Servidor.nombre_servidor = 'SE03';
 +
 +-- Create database link --
 +
 +CREATE PUBLIC DATABASE LINK DLBD1
 +CONNECT TO U1
 +IDENTIFIED BY "U1"
 +USING 'CBD1';
 +
 +-- Create database link without tnsname.ora --
 +
 +CREATE PUBLIC DATABASE LINK DLBD2
 +CONNECT TO system identified BY "root"
 +USING
 +'(DESCRIPTION =
 +    (ADDRESS = (PROTOCOL = TCP)(HOST = 172.17.28.149)(PORT = 1521))
 +    (CONNECT_DATA =
 +      (SERVER = DEDICATED)
 +      (SERVICE_NAME = XE)
 +      (SID = XE)
 +    )
 +  )'
 +/
 +
 +CREATE DATABASE LINK dl1
 +CONNECT TO SYSTEM IDENTIFIED BY root
 +USING 'dl1';

-- Conectarse RMAN --
-- $ rman --
-- connect target sys/root@172.17.29.94; --
-- backup database spfile plus archivelog; --

select job_name, state from all_scheduler_jobs where job_name='SE06';

select job_name, status from all_scheduler_job_run_details where job_name='SE06';
exec sp_dropJob('SE10');
exec sp_createJob('SE06', 'C:\Estrategias\rman\SE06.bat','hola', SYSTIMESTAMP);
exec sp_createJob('PruebaMasterFinal', 'C:\Estrategias\rman\PruebaMasterFinal.bat','hola', SYSTIMESTAMP);

grant create job, create external job to sys;
grant create job, create external job to system;
--JOB CORRECTO SIN PARAMETRIZAR
create or replace procedure sp_createJob(estrategia varchar, ruta varchar, frecuencia varchar, inicio timestamp)
is
begin    
    dbms_scheduler.create_job( 
        job_name        => estrategia, 
        job_type        => 'EXECUTABLE', 
        job_action      => ruta, 
        start_date      => SYSTIMESTAMP, 
        repeat_interval => 'FREQ=MINUTELY;BYMINUTE=5', 
        enabled         => true, 
        comments        => 'Backup database using RMAN');
	commit;
end;
/
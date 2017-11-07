begin 
    dbms_scheduler.drop_job ( 
        job_name    => 'BACKUP_RMAN'); 
end; 
/ 
       
begin 
    dbms_scheduler.create_job( 
        job_name        => 'BACKUP_RMAN', 
        job_type        => 'EXECUTABLE', 
        job_action      => 'C:\rman\rman.bat', 
        start_date      => SYSTIMESTAMP, 
        repeat_interval => 'FREQ=DAILY;BYHOUR=4;BYMINUTE=1', 
        enabled         => false, 
        comments        => 'Backup database using RMAN'); 
end; 
/ 
           
begin 
    dbms_scheduler.run_job(job_name=>'BACKUP_RMAN',USE_CURRENT_SESSION=>true);
end; 
/ 

select * from Nombre_tabla renita@nombre_dblink;

select into nombre_tabla_remota@nombre_dblink
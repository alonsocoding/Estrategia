set ORACLE_HOME=C:\oraclexe\app\oracle\product\11.2.0\server
set ORACLE_SID=XE 
set NLS_DATE_FORMAT="YYYY-MON-DD HH24:MI:SS"

%ORACLE_HOME%\bin\rman log C:\Users\alonso\Desktop\Estrategia\Jobs\Prueba.log cmdfile C:\Users\alonso\Desktop\Estrategia\Jobs\rman.rcv

exit 0
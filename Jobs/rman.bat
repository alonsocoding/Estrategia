set ORACLE_HOME=C:\oraclexe\app\oracle\product\11.2.0\server
set ORACLE_SID=XE 
set NLS_DATE_FORMAT="YYYY-MON-DD HH24:MI:SS"

%ORACLE_HOME%\bin\rman target / log C:\rman\rman.log cmdfile C:\rman\rman.rcv

exit 0
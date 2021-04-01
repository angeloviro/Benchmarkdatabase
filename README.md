DESCRIPTION OF THE APPLICATION
I implemented a simple java application which executes benchmarks on a JDBC compliant database. The application: evaluates the min/max/avg time of INSERT statements;
evaluates the min/max/avg time of SELECT statements (using the PK of the COLUMN). 
The application makes commits every x inserts statement. The application will be run on a PostGRE or MS SQLServer database. 
STRUCTURE OF THE APPLICATION
The application is divided in four packages:
- Benchmark: Classes describes benchmarks about insert and select statements;
- com.mycompany.benchmarkdatabase: There is the class that runs the application;
- Databasemanagement: There is the class used for the connection to the database, close the connection and create the table;
- Importantfunctions: There is the class used for create a random string of characters of lenght given on the input, and for create a random integer number of max value given on the input.
CONFIGURATION 
In "\src\main\resource\Configuration.properties" you must indicate your database's username, password, port number, the database used for the test, server name, in the dbms you must indicate with the number 0 if the dbms' name is Postgresql, 1 if the dbms' name is MS Server. Besides, you must indicate the number of iterations of insert and select, the number of warmup iterations of insert and select, the maximum value of the integer inserted in the table, the maximum size of the varchar inserted in the table.You must indicate the number of inserts, that make a commit. In the classes "Insert" and "Select" in the package "Benchmark",in the class "Runbenchmark" in the package "com.mycompany.benchmarkdatabase" and in the class "Databasemanagement" in the package "Databasemanagement" you must specificate the absolute path of the file "Configure.Properties". The warmup iterations are made before the benchmark.
COMPILATION AND EXECUTION
For the compilation you must digit mvn package.
For the execution you must go to the target directory and then digit: java -cp "lib/*;Benchmarkdatabase-1.0-SNAPSHOT.jar" com.mycompany.Runbenchmark.
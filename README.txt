ONLINE EXAMINATION AND AUTO-EVALUATION SYSTEM
=============================================

Technology:
1. Java 17/21
2. Eclipse IDE
3. Java Swing
4. JDBC
5. Oracle Database
6. SQL*Plus

IMPORTANT:
- Run database/online_exam.sql in SQL*Plus first.
- Change the Oracle username/password in src/app/DBConnection.java.
- Add the Oracle JDBC driver (ojdbc8.jar or ojdbc11.jar) to the Eclipse Build Path.
- Then run src/app/Main.java.

ORACLE SETUP
------------
The SQL script creates the tables, constraints, indexes, view, function,
procedure, trigger, cursor procedure, sample data, and demo queries.

If your Oracle service uses a different service name, change:
jdbc:oracle:thin:@localhost:1521:XE

Common alternatives:
jdbc:oracle:thin:@localhost:1521/XEPDB1
jdbc:oracle:thin:@localhost:1521:ORCL

ECLIPSE SETUP
-------------
1. File -> New -> Java Project.
2. Project name: OnlineExaminationAutoEvaluationSystem.
3. Create package: app
4. Copy the Java files from src/app into the package.
5. Project -> Properties -> Java Build Path -> Libraries.
6. Add External JARs -> select your Oracle JDBC jar.
7. Open DBConnection.java and set USER and PASSWORD.
8. Run Main.java.

DEMO ACCOUNTS
-------------
Student:
Email: student1@gmail.com
Password: 1234

Admin:
Email: admin@gmail.com
Password: admin123

PROJECT FLOW
------------
Login
  -> Student Dashboard
  -> Start Exam
  -> Answer MCQs
  -> Submit
  -> JDBC transaction saves attempt + answers + result
  -> SQL auto-evaluation checks correct options
  -> Result screen displays score

ADMIN
-----
Admin can:
- Add questions/options
- View report
- View ranking
- View all results

DBMS CONCEPTS INCLUDED
----------------------
ER-model based normalized schema
Primary keys
Foreign keys
NOT NULL
UNIQUE
CHECK
DDL
DML
JOIN
Aggregate functions
Subquery
CTE
RANK() window function
View
Stored function
Stored procedure
Cursor
Trigger
Indexes
Transactions
COMMIT / ROLLBACK
ACID concept
Referential integrity
PreparedStatement / SQL injection prevention
Normalization-friendly 3NF design

NOTES
-----
This is an academic project. Passwords are stored as plain text only to keep
the classroom implementation simple. A production system should store
password hashes.

\# Online Examination and Auto-Evaluation System



A desktop-based examination system built using Java Swing, JDBC, and Oracle Database. The project allows students to take multiple-choice examinations and view automatically calculated results, while administrators can manage questions and review student performance.



\## Features



\### Student Module

\- Login to the examination system.

\- View the student dashboard.

\- Attempt multiple-choice questions.

\- Navigate between questions and submit an examination.

\- View examination results and scores.

\- Review previous results where available.



\### Administrator Module

\- Access the administrator dashboard.

\- Add examination questions and answer options.

\- View examination statistics and reports.

\- View student rankings.

\- Review examination results.



\### Automatic Evaluation

\- Records student answers and examination attempts.

\- Evaluates submitted answers against the correct options stored in the database.

\- Calculates scores and percentages.

\- Stores examination results in Oracle Database.

\- Uses JDBC transactions to help maintain consistency during submission.



\## Technology Stack



| Technology | Purpose |

|---|---|

| Java 17 or 21 | Application development |

| Java Swing | Desktop graphical user interface |

| JDBC | Java-to-database connectivity |

| Oracle Database | Persistent data storage |

| SQL and PL/SQL | Database operations and DBMS concepts |

| Eclipse IDE | Development and execution |



\## Project Structure



```text

Online-Examination-and-Auto-Evaluation-System/

├── database/

│   └── online\_exam.sql

├── src/

│   └── app/

│       ├── Main.java

│       ├── LoginFrame.java

│       ├── LoginService.java

│       ├── StudentDashboard.java

│       ├── AdminDashboard.java

│       ├── ExamFrame.java

│       ├── ExamService.java

│       ├── ResultFrame.java

│       ├── ResultService.java

│       └── ...

├── DBMS\_CONCEPT\_MAPPING.txt

├── .gitignore

└── README.md

```



\## Prerequisites



Install or configure the following before running the application:



1\. Java Development Kit (JDK) 17 or 21.

2\. Eclipse IDE for Java development.

3\. Oracle Database with a configured service.

4\. Oracle JDBC driver compatible with your Java and Oracle versions.

5\. SQL\*Plus or another suitable Oracle SQL client.



\## Database Setup



1\. Start your Oracle Database service.

2\. Create or select a dedicated development schema.

3\. Review `database/online\_exam.sql` before executing it.

4\. Execute the SQL script using SQL\*Plus or another Oracle SQL client.

5\. Verify that the tables, constraints, sample data, and other database objects were created successfully.



\*\*Database safety:\*\* The SQL script contains statements that drop existing database objects. Run it only against a disposable development schema or after taking an appropriate backup. Do not execute it against a shared or production database without reviewing and modifying the script.



\## Application Configuration



The application uses `src/app/DBConnection.java` for database connectivity.



Configure the following environment variables before running the application:



\- `EXAM\_DB\_USER`: Oracle database username.

\- `EXAM\_DB\_PASSWORD`: Oracle database password.



For a temporary PowerShell session, set them using:



```powershell

$env:EXAM\_DB\_USER = "your\_database\_username"

$env:EXAM\_DB\_PASSWORD = "your\_database\_password"

```



Use the database connection URL configured in `DBConnection.java`. Update the service name if your Oracle installation uses a different one.



Do not commit actual database credentials, passwords, or private configuration files to GitHub.



\## Running the Application in Eclipse



1\. Open Eclipse and create or import the Java project.

2\. Ensure that the source files are in the `app` package.

3\. Configure the JDK under the project's Java Build Path.

4\. Add the compatible Oracle JDBC driver to the project's libraries.

5\. Set the required database environment variables.

6\. Verify that Oracle Database is running and the schema is configured.

7\. Open `src/app/Main.java`.

8\. Run `Main.java` as a Java application.



\## Database Concepts Demonstrated



The project illustrates several database concepts, including:



\- Entity relationships and relational schema design.

\- Primary keys, foreign keys, and integrity constraints.

\- SQL DDL and DML operations.

\- Joins, aggregate functions, subqueries, and common table expressions.

\- Window functions and ranking.

\- Views, stored procedures, functions, and triggers.

\- Indexing and referential integrity.

\- JDBC transactions and exception handling.

\- Prepared statements for parameterized SQL queries.



For additional explanations, refer to `DBMS\_CONCEPT\_MAPPING.txt`.



\## Troubleshooting



\*\*Database connection fails\*\*

\- Confirm that the Oracle service is running.

\- Check the connection URL, port, and service name.

\- Verify the database username and password.

\- Ensure that the JDBC driver is available on the project's build path.



\*\*Application does not start\*\*

\- Confirm that the configured JDK is compatible with the project.

\- Check that `Main.java` is in the correct package.

\- Review the Eclipse console for compilation or runtime errors.



\*\*Login fails\*\*

\- Verify that the required student or administrator records exist in the database.

\- Check that the application's login logic matches the configured database schema.

\- Review the application console for errors without exposing passwords or other sensitive information.



\## Security Note



This project is intended for academic and demonstration purposes. Before using it with real student data, review the authentication implementation, password storage, session handling, database permissions, and error logging. Use secure password hashing and remove hardcoded credentials and password debug statements.



\## Team



This project was developed collaboratively by:



\- \*\*\[Team Member 1]\*\* — \[Actual contribution]

\- \*\*\[Team Member 2]\*\* — \[Actual contribution]

\- \*\*\[Team Member 3]\*\* — \[Actual contribution]

\- \*\*\[Team Member 4]\*\* — \[Actual contribution]



Replace the placeholders with your team's names and their actual contributions.



\## License



Add the appropriate license information if the project is being distributed publicly.

My Contribution

I contributed to the Online Examination and Auto-Evaluation System
by improving the project documentation. This system helps students
take online examinations and receive automatically evaluated results.
It uses Java, Swing, JDBC, and Oracle Database.


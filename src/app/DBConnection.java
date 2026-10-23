
package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:oracle:thin:@localhost:1521:orcl";

    private static final String USER =
            System.getenv("EXAM_DB_USER");

    private static final String PASSWORD =
            System.getenv("EXAM_DB_PASSWORD");

    public static Connection getConnection() throws SQLException {
        if (USER == null || PASSWORD == null) {
            throw new SQLException(
                "Set EXAM_DB_USER and EXAM_DB_PASSWORD environment variables."
            );
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

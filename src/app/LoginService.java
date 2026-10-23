package app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginService {

    public User login(String email, String password) throws Exception {

        String sql =
                "SELECT student_id, student_name, email " +
                "FROM student WHERE email = ? AND password = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

        	ps.setString(1, email);
        	ps.setString(2, password);

        	System.out.println("Login email: [" + email + "]");
        	System.out.println("Login password: [" + password + "]");

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Student(
                            rs.getInt("student_id"),
                            rs.getString("student_name"),
                            rs.getString("email")
                    );
                }
            }
        }

        // Classroom admin account.
        if (email.equals("admin@gmail.com") &&
            password.equals("admin123")) {
            return new Admin(0, "Administrator", email);
        }

        return null;
    }
}

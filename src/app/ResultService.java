package app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ResultService {

    public String getStudentResults(int studentId) throws Exception {

        String sql =
                "SELECT exam_name, attempt_id, score, percentage, " +
                "correct, wrong, result_time " +
                "FROM student_result_report " +
                "WHERE student_id = ? " +
                "ORDER BY result_time DESC";

        StringBuilder sb = new StringBuilder();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    sb.append("Exam: ")
                      .append(rs.getString("exam_name"))
                      .append("\n");

                    sb.append("Attempt ID: ")
                      .append(rs.getInt("attempt_id"))
                      .append("\n");

                    sb.append("Score: ")
                      .append(rs.getDouble("score"))
                      .append("\n");

                    sb.append("Percentage: ")
                      .append(rs.getDouble("percentage"))
                      .append("%\n");

                    sb.append("Correct: ")
                      .append(rs.getInt("correct"))
                      .append("   Wrong: ")
                      .append(rs.getInt("wrong"))
                      .append("\n");

                    sb.append("--------------------------------\n");
                }
            }
        }

        if (sb.length() == 0) {
            return "No results found.";
        }

        return sb.toString();
    }
}

package app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminService {

    public String getReport() throws Exception {

        String sql =
                "SELECT COUNT(*) total_results, " +
                "NVL(AVG(score),0) average_score, " +
                "NVL(MAX(score),0) highest_score, " +
                "NVL(MIN(score),0) lowest_score " +
                "FROM result";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return "TOTAL RESULTS : " + rs.getInt("total_results") +
                       "\nAVERAGE SCORE : " + rs.getDouble("average_score") +
                       "\nHIGHEST SCORE : " + rs.getDouble("highest_score") +
                       "\nLOWEST SCORE : " + rs.getDouble("lowest_score");
            }
        }

        return "No report available.";
    }

    public String getRanking() throws Exception {

        String sql =
                "SELECT student_name, score, " +
                "RANK() OVER (ORDER BY score DESC) AS student_rank " +
                "FROM (" +
                "SELECT s.student_name, r.score " +
                "FROM student s " +
                "JOIN exam_attempt ea ON s.student_id=ea.student_id " +
                "JOIN result r ON ea.attempt_id=r.attempt_id" +
                ")";

        StringBuilder sb = new StringBuilder();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                sb.append("Rank ")
                  .append(rs.getInt("student_rank"))
                  .append(" : ")
                  .append(rs.getString("student_name"))
                  .append(" - ")
                  .append(rs.getDouble("score"))
                  .append("\n");
            }
        }

        if (sb.length() == 0) {
            return "No ranking data.";
        }

        return sb.toString();
    }

    public void addQuestion(
            int examId,
            String questionText,
            String[] optionText,
            int correctIndex) throws Exception {

        Connection con = null;

        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            String qSql =
                    "INSERT INTO question(exam_id,question_text,marks) " +
                    "VALUES(?,?,1)";

            int questionId;

            try (PreparedStatement ps = con.prepareStatement(
                    qSql, new String[]{"QUESTION_ID"})) {

                ps.setInt(1, examId);
                ps.setString(2, questionText);
                ps.executeUpdate();

                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (!keys.next()) {
                        throw new Exception("Question ID not generated.");
                    }
                    questionId = keys.getInt(1);
                }
            }

            String oSql =
                    "INSERT INTO question_option(" +
                    "question_id,option_label,option_text,is_correct" +
                    ") VALUES(?,?,?,?)";

            try (PreparedStatement ps = con.prepareStatement(oSql)) {

                String[] labels = {"A","B","C","D"};

                for (int i = 0; i < 4; i++) {
                    ps.setInt(1, questionId);
                    ps.setString(2, labels[i]);
                    ps.setString(3, optionText[i]);
                    ps.setString(4, i == correctIndex ? "Y" : "N");
                    ps.addBatch();
                }

                ps.executeBatch();
            }

            con.commit();

        } catch (Exception e) {

            if (con != null) {
                con.rollback();
            }

            throw e;

        } finally {

            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        }
    }
}

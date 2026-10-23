package app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ExamService {

    public Exam getExam() throws Exception {

        String sql =
                "SELECT exam_id, exam_name, duration_minutes, total_marks " +
                "FROM exam WHERE exam_id = 1";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return new Exam(
                        rs.getInt("exam_id"),
                        rs.getString("exam_name"),
                        rs.getInt("duration_minutes"),
                        rs.getDouble("total_marks")
                );
            }
        }

        return null;
    }

    public List<Question> getQuestions(int examId) throws Exception {

        List<Question> list = new ArrayList<>();

        String qSql =
                "SELECT question_id, question_text, marks " +
                "FROM question WHERE exam_id = ? " +
                "ORDER BY question_id";

        String oSql =
                "SELECT option_id, option_label, option_text " +
                "FROM question_option WHERE question_id = ? " +
                "ORDER BY option_label";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement qps = con.prepareStatement(qSql);
             PreparedStatement ops = con.prepareStatement(oSql)) {

            qps.setInt(1, examId);

            try (ResultSet qrs = qps.executeQuery()) {

                while (qrs.next()) {

                    Question q = new Question(
                            qrs.getInt("question_id"),
                            qrs.getString("question_text"),
                            qrs.getDouble("marks")
                    );

                    ops.setInt(1, q.getQuestionId());

                    try (ResultSet ors = ops.executeQuery()) {
                        while (ors.next()) {
                            q.addOption(
                                    new Question.Option(
                                            ors.getInt("option_id"),
                                            ors.getString("option_label"),
                                            ors.getString("option_text")
                                    )
                            );
                        }
                    }

                    list.add(q);
                }
            }
        }

        return list;
    }

    public int createAttempt(int studentId, int examId) throws Exception {

        String sql =
                "INSERT INTO exam_attempt(student_id, exam_id, status) " +
                "VALUES (?, ?, 'STARTED')";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     sql, new String[]{"ATTEMPT_ID"})) {

            ps.setInt(1, studentId);
            ps.setInt(2, examId);

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }

        throw new Exception("Could not create exam attempt.");
    }

    public Result submitExam(
            int attemptId,
            List<Question> questions,
            int[] selectedOptionIds) throws Exception {

        String insertAnswer =
                "INSERT INTO student_answer(" +
                "attempt_id, question_id, option_id, is_correct) " +
                "VALUES (?, ?, ?, ?)";

        String correctSql =
                "SELECT is_correct FROM question_option " +
                "WHERE option_id = ?";

        Connection con = null;

        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            int attempted = 0;
            int correct = 0;
            int wrong = 0;
            double score = 0;

            try (PreparedStatement answerPs = con.prepareStatement(insertAnswer);
                 PreparedStatement correctPs = con.prepareStatement(correctSql)) {

                for (int i = 0; i < questions.size(); i++) {

                    int selected = selectedOptionIds[i];
                    String isCorrect = "N";

                    if (selected != -1) {
                        attempted++;

                        correctPs.setInt(1, selected);

                        try (ResultSet rs = correctPs.executeQuery()) {
                            if (rs.next() && rs.getString(1).equals("Y")) {
                                isCorrect = "Y";
                                correct++;
                                score += questions.get(i).getMarks();
                            } else {
                                wrong++;
                            }
                        }
                    }

                    answerPs.setInt(1, attemptId);
                    answerPs.setInt(2, questions.get(i).getQuestionId());

                    if (selected == -1) {
                        answerPs.setNull(3, java.sql.Types.INTEGER);
                    } else {
                        answerPs.setInt(3, selected);
                    }

                    answerPs.setString(4, isCorrect);
                    answerPs.addBatch();
                }

                answerPs.executeBatch();
            }

            int totalQuestions = questions.size();
            double percentage =
                    totalQuestions == 0
                    ? 0
                    : (score / totalQuestions) * 100.0;

            String updateAttempt =
                    "UPDATE exam_attempt " +
                    "SET status='SUBMITTED', end_time=SYSTIMESTAMP " +
                    "WHERE attempt_id=?";

            try (PreparedStatement ps = con.prepareStatement(updateAttempt)) {
                ps.setInt(1, attemptId);
                ps.executeUpdate();
            }

            String resultSql =
                    "INSERT INTO result(" +
                    "attempt_id,total_questions,attempted,correct,wrong,score,percentage" +
                    ") VALUES (?,?,?,?,?,?,?)";

            try (PreparedStatement ps = con.prepareStatement(resultSql)) {
                ps.setInt(1, attemptId);
                ps.setInt(2, totalQuestions);
                ps.setInt(3, attempted);
                ps.setInt(4, correct);
                ps.setInt(5, wrong);
                ps.setDouble(6, score);
                ps.setDouble(7, percentage);
                ps.executeUpdate();
            }

            // Trigger trg_result_audit executes automatically here.

            con.commit();

            return new Result(
                    totalQuestions,
                    attempted,
                    correct,
                    wrong,
                    score,
                    percentage
            );

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

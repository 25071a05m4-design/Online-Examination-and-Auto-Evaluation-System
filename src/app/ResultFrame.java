package app;

import javax.swing.*;
import java.awt.*;

public class ResultFrame extends JFrame {

    public ResultFrame(Student student, Result result) {

        setTitle("Exam Result");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTextArea area = new JTextArea();

        area.setEditable(false);
        area.setFont(new Font("Arial", Font.PLAIN, 18));

        area.setText(
                "ONLINE EXAMINATION RESULT\n\n" +
                "Student: " + student.getName() + "\n\n" +
                "Total Questions : " +
                result.getTotalQuestions() + "\n" +
                "Attempted       : " +
                result.getAttempted() + "\n" +
                "Correct         : " +
                result.getCorrect() + "\n" +
                "Wrong           : " +
                result.getWrong() + "\n" +
                "Score           : " +
                result.getScore() + "\n" +
                "Percentage      : " +
                String.format("%.2f", result.getPercentage()) + "%\n\n" +
                "Auto-evaluation completed."
        );

        JButton dashboard = new JButton("Back to Dashboard");

        add(new JScrollPane(area), BorderLayout.CENTER);
        add(dashboard, BorderLayout.SOUTH);

        dashboard.addActionListener(e -> {
            dispose();
            new StudentDashboard(student);
        });

        setVisible(true);
    }
}

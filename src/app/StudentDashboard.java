package app;

import javax.swing.*;
import java.awt.*;

public class StudentDashboard extends JFrame {

    private Student student;

    public StudentDashboard(Student student) {

        this.student = student;

        setTitle("Student Dashboard");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel welcome = new JLabel(
                student.getRoleMessage(),
                SwingConstants.CENTER
        );

        JButton startExam = new JButton("Start Exam");
        JButton viewResults = new JButton("View My Results");
        JButton logout = new JButton("Logout");

        JPanel buttons = new JPanel(
                new GridLayout(3, 1, 10, 10)
        );

        buttons.setBorder(
                BorderFactory.createEmptyBorder(30, 80, 30, 80)
        );

        buttons.add(startExam);
        buttons.add(viewResults);
        buttons.add(logout);

        add(welcome, BorderLayout.NORTH);
        add(buttons, BorderLayout.CENTER);

        startExam.addActionListener(e -> startExam());
        viewResults.addActionListener(e -> viewResults());

        logout.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        setVisible(true);
    }

    private void startExam() {

        try {
            ExamService service = new ExamService();
            Exam exam = service.getExam();

            if (exam == null) {
                JOptionPane.showMessageDialog(this, "No exam found.");
                return;
            }

            int attemptId =
                    service.createAttempt(
                            student.getId(),
                            exam.getExamId()
                    );

            dispose();

            new ExamFrame(
                    student,
                    exam,
                    attemptId
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to start exam:\n" + ex.getMessage()
            );
        }
    }

    private void viewResults() {

        try {

            String results =
                    new ResultService()
                    .getStudentResults(student.getId());

            JOptionPane.showMessageDialog(
                    this,
                    results,
                    "My Results",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load results:\n" + ex.getMessage()
            );
        }
    }
}

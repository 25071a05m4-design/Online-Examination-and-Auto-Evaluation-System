package app;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard(Admin admin) {

        setTitle("Administrator Dashboard");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel title = new JLabel(
                admin.getRoleMessage(),
                SwingConstants.CENTER
        );

        JButton reportButton = new JButton("View Statistics");
        JButton rankingButton = new JButton("View Student Ranking");
        JButton addQuestionButton = new JButton("Add Question");
        JButton logoutButton = new JButton("Logout");

        JPanel panel = new JPanel(
                new GridLayout(4, 1, 10, 10)
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(30, 100, 30, 100)
        );

        panel.add(reportButton);
        panel.add(rankingButton);
        panel.add(addQuestionButton);
        panel.add(logoutButton);

        add(title, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        reportButton.addActionListener(e -> showReport());
        rankingButton.addActionListener(e -> showRanking());

        addQuestionButton.addActionListener(
                e -> new AddQuestionFrame()
        );

        logoutButton.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        setVisible(true);
    }

    private void showReport() {

        try {

            String report =
                    new AdminService().getReport();

            JOptionPane.showMessageDialog(
                    this,
                    report,
                    "Aggregate DBMS Report",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error:\n" + ex.getMessage()
            );
        }
    }

    private void showRanking() {

        try {

            String ranking =
                    new AdminService().getRanking();

            JOptionPane.showMessageDialog(
                    this,
                    ranking,
                    "Student Ranking - RANK()",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error:\n" + ex.getMessage()
            );
        }
    }
}

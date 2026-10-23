package app;

import javax.swing.*;
import java.awt.*;

public class AddQuestionFrame extends JFrame {

    private JTextField questionField;
    private JTextField[] optionFields;
    private JComboBox<String> correctBox;

    public AddQuestionFrame() {

        setTitle("Add Question");
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(
                new GridLayout(7, 2, 8, 8)
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(20, 30, 20, 30)
        );

        questionField = new JTextField();

        optionFields = new JTextField[4];

        for (int i = 0; i < 4; i++) {
            optionFields[i] = new JTextField();
        }

        correctBox = new JComboBox<>(
                new String[]{"A", "B", "C", "D"}
        );

        JButton addButton = new JButton("Add Question");

        panel.add(new JLabel("Exam ID:"));
        JTextField examIdField = new JTextField("1");
        panel.add(examIdField);

        panel.add(new JLabel("Question:"));
        panel.add(questionField);

        for (int i = 0; i < 4; i++) {
            panel.add(new JLabel("Option " + (char)('A' + i) + ":"));
            panel.add(optionFields[i]);
        }

        panel.add(new JLabel("Correct Option:"));
        panel.add(correctBox);

        add(panel, BorderLayout.CENTER);
        add(addButton, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {

            try {

                int examId =
                        Integer.parseInt(examIdField.getText().trim());

                String[] options = new String[4];

                for (int i = 0; i < 4; i++) {
                    options[i] = optionFields[i].getText().trim();

                    if (options[i].isEmpty()) {
                        throw new Exception(
                                "All options are required."
                        );
                    }
                }

                int correctIndex =
                        correctBox.getSelectedIndex();

                new AdminService().addQuestion(
                        examId,
                        questionField.getText().trim(),
                        options,
                        correctIndex
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Question added successfully."
                );

                dispose();

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Could not add question:\n" +
                        ex.getMessage()
                );
            }
        });

        setVisible(true);
    }
}

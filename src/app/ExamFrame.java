package app;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ExamFrame extends JFrame {

    private Student student;
    private Exam exam;
    private int attemptId;

    private List<Question> questions;
    private int current = 0;
    private int[] selectedOptions;

    private JLabel questionLabel;
    private JRadioButton[] optionButtons;
    private ButtonGroup group;

    private JButton previous;
    private JButton next;
    private JButton submit;

    private JLabel timerLabel;

    private int secondsRemaining;
    private Timer timer;

    public ExamFrame(
            Student student,
            Exam exam,
            int attemptId) throws Exception {

        this.student = student;
        this.exam = exam;
        this.attemptId = attemptId;

        ExamService service = new ExamService();
        questions = service.getQuestions(exam.getExamId());

        selectedOptions = new int[questions.size()];

        for (int i = 0; i < selectedOptions.length; i++) {
            selectedOptions[i] = -1;
        }

        secondsRemaining = exam.getDurationMinutes() * 60;

        buildUI();
        loadQuestion();

        setVisible(true);

        startTimer();
    }

    private void buildUI() {

        setTitle(exam.getExamName());
        setSize(650, 450);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        questionLabel = new JLabel();

        JPanel center = new JPanel(
                new GridLayout(5, 1, 5, 5)
        );

        optionButtons = new JRadioButton[4];

        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            center.add(optionButtons[i]);
        }

        group = new ButtonGroup();

        for (JRadioButton button : optionButtons) {
            group.add(button);
        }

        previous = new JButton("Previous");
        next = new JButton("Next");
        submit = new JButton("Submit Exam");

        timerLabel = new JLabel("Time", SwingConstants.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());
        JPanel buttons = new JPanel();
        buttons.add(previous);
        buttons.add(next);
        buttons.add(submit);
        bottom.add(timerLabel, BorderLayout.NORTH);
        bottom.add(buttons, BorderLayout.SOUTH);

        add(questionLabel, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        previous.addActionListener(e -> {
            saveCurrentAnswer();

            if (current > 0) {
                current--;
                loadQuestion();
            }
        });

        next.addActionListener(e -> {
            saveCurrentAnswer();

            if (current < questions.size() - 1) {
                current++;
                loadQuestion();
            }
        });

        submit.addActionListener(e -> submitExam());
    }

    private void loadQuestion() {

        Question q = questions.get(current);

        questionLabel.setText(
                "Question " + (current + 1) +
                " of " + questions.size() +
                ": " + q.getQuestionText()
        );

        group.clearSelection();

        for (int i = 0; i < 4; i++) {

            Question.Option option = q.getOptions().get(i);

            optionButtons[i].setText(
                    option.getLabel() + ". " +
                    option.getText()
            );

            if (selectedOptions[current] ==
                    option.getOptionId()) {

                optionButtons[i].setSelected(true);
            }
        }

        previous.setEnabled(current > 0);
        next.setEnabled(current < questions.size() - 1);
    }

    private void saveCurrentAnswer() {

        for (int i = 0; i < 4; i++) {

            if (optionButtons[i].isSelected()) {

                selectedOptions[current] =
                        questions.get(current)
                        .getOptions()
                        .get(i)
                        .getOptionId();

                return;
            }
        }

        selectedOptions[current] = -1;
    }

    private void submitExam() {

        saveCurrentAnswer();

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to submit?",
                "Confirm Submission",
                JOptionPane.YES_NO_OPTION
        );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        try {

            stopTimer();

            Result result =
                    new ExamService().submitExam(
                            attemptId,
                            questions,
                            selectedOptions
                    );

            dispose();

            new ResultFrame(student, result);

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Submission failed. Transaction was rolled back.\n" +
                    ex.getMessage()
            );
        }
    }

    private void startTimer() {

        timer = new Timer(1000, e -> {

            secondsRemaining--;

            int minutes = secondsRemaining / 60;
            int seconds = secondsRemaining % 60;

            timerLabel.setText(
                    String.format(
                            "Time Remaining: %02d:%02d",
                            minutes,
                            seconds
                    )
            );

            if (secondsRemaining <= 0) {

                stopTimer();

                JOptionPane.showMessageDialog(
                        this,
                        "Time is over. Exam will be submitted."
                );

                saveCurrentAnswer();

                try {

                    Result result =
                            new ExamService().submitExam(
                                    attemptId,
                                    questions,
                                    selectedOptions
                            );

                    dispose();

                    new ResultFrame(student, result);

                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Automatic submission failed:\n" +
                            ex.getMessage()
                    );
                }
            }
        });

        timer.start();
    }

    private void stopTimer() {

        if (timer != null) {
            timer.stop();
        }
    }
}

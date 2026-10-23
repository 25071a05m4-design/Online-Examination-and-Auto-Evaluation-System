package app;

import java.util.ArrayList;
import java.util.List;

public class Question {

    private int questionId;
    private String questionText;
    private double marks;
    private List<Option> options = new ArrayList<>();

    public Question(int questionId, String questionText, double marks) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.marks = marks;
    }

    public void addOption(Option option) {
        options.add(option);
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public double getMarks() {
        return marks;
    }

    public List<Option> getOptions() {
        return options;
    }

    public static class Option {
        private int optionId;
        private String label;
        private String text;

        public Option(int optionId, String label, String text) {
            this.optionId = optionId;
            this.label = label;
            this.text = text;
        }

        public int getOptionId() {
            return optionId;
        }

        public String getLabel() {
            return label;
        }

        public String getText() {
            return text;
        }
    }
}

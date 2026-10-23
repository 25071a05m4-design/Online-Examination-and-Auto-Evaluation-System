package app;

public class Result {

    private int totalQuestions;
    private int attempted;
    private int correct;
    private int wrong;
    private double score;
    private double percentage;

    public Result(int totalQuestions, int attempted, int correct,
                  int wrong, double score, double percentage) {
        this.totalQuestions = totalQuestions;
        this.attempted = attempted;
        this.correct = correct;
        this.wrong = wrong;
        this.score = score;
        this.percentage = percentage;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public int getAttempted() {
        return attempted;
    }

    public int getCorrect() {
        return correct;
    }

    public int getWrong() {
        return wrong;
    }

    public double getScore() {
        return score;
    }

    public double getPercentage() {
        return percentage;
    }
}

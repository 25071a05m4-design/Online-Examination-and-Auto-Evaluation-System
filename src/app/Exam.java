package app;

public class Exam {

    private int examId;
    private String examName;
    private int durationMinutes;
    private double totalMarks;

    public Exam(int examId, String examName, int durationMinutes, double totalMarks) {
        this.examId = examId;
        this.examName = examName;
        this.durationMinutes = durationMinutes;
        this.totalMarks = totalMarks;
    }

    public int getExamId() {
        return examId;
    }

    public String getExamName() {
        return examName;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public double getTotalMarks() {
        return totalMarks;
    }
}

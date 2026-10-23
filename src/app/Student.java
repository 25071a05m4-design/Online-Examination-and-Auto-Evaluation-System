package app;

public class Student extends User {

    public Student(int id, String name, String email) {
        super(id, name, email);
    }

    @Override
    public String getRoleMessage() {
        return "Welcome Student, " + name;
    }
}

package app;

public class Admin extends User {

    public Admin(int id, String name, String email) {
        super(id, name, email);
    }

    @Override
    public String getRoleMessage() {
        return "Welcome Administrator, " + name;
    }
}

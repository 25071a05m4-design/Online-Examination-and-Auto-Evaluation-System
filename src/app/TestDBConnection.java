package app;

import java.sql.Connection;

public class TestDBConnection {

    public static void main(String[] args) {

        try {
            Connection con = DBConnection.getConnection();

            System.out.println("Oracle Database Connected Successfully!");

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
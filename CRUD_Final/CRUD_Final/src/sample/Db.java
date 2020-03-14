package sample;

import java.sql.Connection;
import java.sql.DriverManager;

public class Db {
    private static Connection connection = null;
    public static Connection connector() throws Exception{
        connection = DriverManager.getConnection("jdbc:mysql://localhost/clients","root","root");
        return connection;
    }
}

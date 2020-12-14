

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class DB_Connection {

    private final String url = "jdbc:postgresql://localhost/userservice";
    private final String user = "postgres";
    private final String password = "root";

    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgresSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

}
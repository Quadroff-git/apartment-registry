package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    protected Connection connection;

    public ConnectionManager(String url, String login, String password) throws SQLException {
        this.connection = DriverManager.getConnection(url, login, password);
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }


    public static void main(String[] argv) {
        try {
            ConnectionManager conn = new ConnectionManager(argv[0], argv[1], argv[2]);
            System.out.println(conn.getConnection().isValid(0));
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

}


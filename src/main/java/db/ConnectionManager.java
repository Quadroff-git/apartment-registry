package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private String url;
    private String login;
    private String password;

    public ConnectionManager(String url, String login, String password) {
        this.url = url;
        this.login = login;
        this.password = password;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, login, password);
    }
}


package db;

import db.dao.BaseDao;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    private ConnectionManager connectionManager;
    private Connection connection;

    public TransactionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        this.connection = null;
    }

    public void initTransaction(BaseDao dao, BaseDao... daos) {
        try {
            if (connection == null) {
                connection = connectionManager.getConnection();
            }
            connection.setAutoCommit(false);

            dao.setConnection(connection);
            for (BaseDao d : daos) {
                d.setConnection(connection);
            }
        } catch (SQLException e) {
            System.out.println("ERROR: Connection initialization failed");
        }
    }

    public void endTransaction() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("ERROR: Connection closure failed");
        }
        connection = null;
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            System.out.println("ERROR: Connection commit failed");
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println("ERROR: Connection rollback failed");
        }
    }
}

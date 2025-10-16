package db.dao;

import db.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    private ConnectionManager connectionManager;
    private Connection connection;

    public TransactionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        this.connection = null;
    }

    public void initTransaction(BaseDao dao, BaseDao... daos) throws SQLException {
        if (connection == null) {
            connection = connectionManager.getConnection();
        }
        connection.setAutoCommit(false);

        dao.setConnection(connection);
        for (BaseDao d : daos) {
            d.setConnection(connection);
        }
    }

    public void endTransaction() throws SQLException{
        connection.close();
        connection = null;
    }

    public void commit() throws SQLException {
        connection.commit();
    }

    public void rollback() throws SQLException {
        connection.rollback();
    }
}

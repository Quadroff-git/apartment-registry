package service;

import db.ConnectionManager;
import db.dao.TransactionManager;

import java.sql.SQLException;
import java.util.List;

public abstract class BaseService<T> {
    protected TransactionManager transactionManager;

    public BaseService(ConnectionManager connectionManager) {
        this.transactionManager = new TransactionManager(connectionManager);
    }

    public abstract List<T> getAll() throws SQLException;
    public abstract T getById(int id) throws SQLException;
    public abstract boolean create(T entity) throws SQLException;
    public abstract boolean delete(int id) throws SQLException;
    public abstract boolean update(T entity) throws SQLException;
}

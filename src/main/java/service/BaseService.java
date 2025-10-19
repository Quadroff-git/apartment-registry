package service;

import db.ConnectionManager;
import db.TransactionManager;

import java.util.List;

public abstract class BaseService<T> {
    protected TransactionManager transactionManager;

    public BaseService(ConnectionManager connectionManager) {
        this.transactionManager = new TransactionManager(connectionManager);
    }

    public abstract List<T> getAll() throws ServiceException;
    public abstract T getById(int id) throws ServiceException;
    public abstract boolean create(T entity) throws ServiceException;
    public abstract boolean delete(int id) throws ServiceException;
    public abstract boolean update(T entity) throws ServiceException;
}

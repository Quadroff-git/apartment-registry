package service;

import db.ConnectionManager;

import java.util.List;

public abstract class BaseService<T> {
    protected ConnectionManager connectionManager;

    public BaseService(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public abstract List<T> getAll();
    public abstract T getById(int id);
    public abstract boolean create(T entity);
    public abstract boolean delete(int id);
    public abstract boolean update(T entity);
}

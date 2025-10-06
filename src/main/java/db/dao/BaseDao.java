package db.dao;

import java.sql.Connection;
import java.util.List;

public abstract class BaseDao<T> {
    private Connection connection;

    public BaseDao(Connection connection) {
        this.connection = connection;
    }

    public abstract List<T> getAll();
    public abstract T findById(int id);
    public abstract boolean delete(int id);
    public abstract boolean delete(T entity);
    public abstract boolean create(T entity);
    public abstract T update(T entity);
}

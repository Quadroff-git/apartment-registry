package db.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class BaseDao<T> {
    protected Connection connection;

    public BaseDao(Connection connection) {
        this.connection = connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public abstract List<T> getAll() throws SQLException;
    public abstract T findById(int id) throws SQLException;
    public abstract boolean delete(int id) throws SQLException;
    public abstract boolean delete(T entity) throws SQLException;
    public abstract boolean create(T entity) throws SQLException;
    public abstract T update(T entity) throws SQLException;
}

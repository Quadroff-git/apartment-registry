package db.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class BaseDao<T> {
    protected Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public abstract List<T> getAll() throws DaoException;
    public abstract T findById(int id) throws DaoException;
    public abstract boolean delete(int id) throws DaoException;
    public abstract boolean delete(T entity) throws DaoException;
    public abstract boolean create(T entity) throws DaoException;
    public abstract T update(T entity) throws DaoException;
}

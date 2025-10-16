package service;

import java.util.List;

public abstract class BaseService<T> {
    public abstract List<T> getAll();
    public abstract T getById(int id);
    public abstract boolean create(T entity);
    public abstract boolean delete(int id);
    public abstract boolean update(T entity);
}

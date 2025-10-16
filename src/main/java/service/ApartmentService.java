package service;

import db.ConnectionManager;
import domain.Apartment;

import java.util.List;

public class ApartmentService extends BaseService<Apartment> {

    public ApartmentService(ConnectionManager connectionManager) {
        super(connectionManager);
    }


    @Override
    public List<Apartment> getAll() {
        return List.of();
    }

    @Override
    public Apartment getById(int id) {
        return null;
    }

    @Override
    public boolean create(Apartment entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean update(Apartment entity) {
        return false;
    }
}

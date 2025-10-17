package service;

import db.ConnectionManager;
import domain.PurchaseRequest;

import java.sql.SQLException;
import java.util.List;

public class PurchaseRequestService extends BaseService<PurchaseRequest> {

    public PurchaseRequestService(ConnectionManager connectionManager) {
        super(connectionManager);
    }

    @Override
    public List<PurchaseRequest> getAll() throws SQLException {
        return List.of();
    }

    @Override
    public PurchaseRequest getById(int id) throws SQLException {
        return null;
    }

    @Override
    public boolean create(PurchaseRequest entity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return false;
    }

    @Override
    public boolean update(PurchaseRequest entity) throws SQLException {
        return false;
    }
}

package db.dao;

import domain.PurchaseRequest;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PurchaseRequestDao extends BaseDao<PurchaseRequest> {

    public PurchaseRequestDao(Connection connection) {
        super(connection);
    }


    @Override
    public List<PurchaseRequest> getAll() throws SQLException {
        return List.of();
    }

    @Override
    public PurchaseRequest findById(int id) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(PurchaseRequest entity) throws SQLException {
        return false;
    }

    @Override
    public boolean create(PurchaseRequest entity) throws SQLException {
        return false;
    }

    @Override
    public PurchaseRequest update(PurchaseRequest entity) throws SQLException {
        return null;
    }
}

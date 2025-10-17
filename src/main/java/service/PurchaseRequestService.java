package service;

import db.ConnectionManager;
import db.dao.ApartmentDao;
import db.dao.ClientDao;
import db.dao.PurchaseRequestDao;
import domain.Apartment;
import domain.PurchaseRequest;

import java.sql.SQLException;
import java.util.List;

public class PurchaseRequestService extends BaseService<PurchaseRequest> {

    public PurchaseRequestService(ConnectionManager connectionManager) {
        super(connectionManager);
    }

    @Override
    public List<PurchaseRequest> getAll() throws SQLException {
        PurchaseRequestDao purchaseRequestDao = new PurchaseRequestDao();
        ClientDao clientDao = new ClientDao();
        transactionManager.initTransaction(purchaseRequestDao, clientDao);

        List<PurchaseRequest> purchaseRequests = purchaseRequestDao.getAll();
        for (PurchaseRequest pr : purchaseRequests) {
            clientDao.loadClient(pr);
        }

        transactionManager.endTransaction();
        return purchaseRequests;
    }

    @Override
    public PurchaseRequest getById(int id) throws SQLException {
        if (id < 0) {
            throw new IllegalArgumentException("id can't be negative");
        }

        PurchaseRequestDao purchaseRequestDao = new PurchaseRequestDao();
        ClientDao clientDao = new ClientDao();
        transactionManager.initTransaction(purchaseRequestDao, clientDao);

        PurchaseRequest purchaseRequest = purchaseRequestDao.findById(id);
        clientDao.loadClient(purchaseRequest);

        transactionManager.endTransaction();
        return purchaseRequest;
    }

    @Override
    public boolean create(PurchaseRequest entity) throws SQLException {
        if (entity == null) {
            throw new IllegalArgumentException("Purchase request passed as argument is null");
        }

        PurchaseRequestDao purchaseRequestDao = new PurchaseRequestDao();
        ClientDao clientDao = new ClientDao();
        transactionManager.initTransaction(purchaseRequestDao, clientDao);

        // Handling the case where entity's client field contains a freshly created object that isn't on the db yet
        if (entity.getClient().getId() != null) {
            try {
                boolean res = purchaseRequestDao.create(entity);

                if (res) {
                    transactionManager.commit();
                }
                transactionManager.endTransaction();

                return res;
            } catch (SQLException e) {
                transactionManager.rollback();
                transactionManager.endTransaction();
                throw e;
            }
        } else {
            try {
                boolean res = purchaseRequestDao.create(entity) && clientDao.create(entity.getClient());

                if (res) {
                    transactionManager.commit();
                }
                transactionManager.endTransaction();

                return res;
            } catch (SQLException e) {
                transactionManager.rollback();
                transactionManager.endTransaction();
                throw e;
            }
        }
    }

    public List<Apartment> createWithCheck(PurchaseRequest entity) throws SQLException {
        if (entity == null) {
            throw new IllegalArgumentException("Purchase request passed as argument is null");
        }

        PurchaseRequestDao purchaseRequestDao = new PurchaseRequestDao();
        ApartmentDao apartmentDao = new ApartmentDao();
        transactionManager.initTransaction(purchaseRequestDao, apartmentDao);

        List<Apartment> results = apartmentDao.findByPurchaseRequest(entity);
        transactionManager.endTransaction();

        if (results.isEmpty()) {
            if (!create(entity)) {
                throw new RuntimeException("Create failed");
            }
        }

        return results;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        if (id < 0) {
            throw new IllegalArgumentException("id can't be negative");
        }

        PurchaseRequestDao purchaseRequestDao = new PurchaseRequestDao();
        transactionManager.initTransaction(purchaseRequestDao);
        try {
            boolean res = purchaseRequestDao.delete(id);

            if (res) {
                transactionManager.commit();
            }
            transactionManager.endTransaction();

            return res;
        } catch (SQLException e) {
            transactionManager.rollback();
            transactionManager.endTransaction();
            throw e;
        }
    }

    @Override
    public boolean update(PurchaseRequest entity) throws SQLException {
        if (entity == null) {
            throw new IllegalArgumentException("Purchase request passed as argument is null");
        }

        PurchaseRequestDao purchaseRequestDao = new PurchaseRequestDao();

        transactionManager.initTransaction(purchaseRequestDao);
        try {
            boolean res = (purchaseRequestDao.update(entity) != null);

            if (res) {
                transactionManager.commit();
            }
            transactionManager.endTransaction();

            return res;
        } catch (SQLException e) {
            transactionManager.rollback();
            transactionManager.endTransaction();
            throw e;
        }
    }
}

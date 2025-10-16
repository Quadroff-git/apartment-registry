package service;

import db.ConnectionManager;
import db.dao.ApartmentDao;
import db.dao.ClientDao;
import db.dao.PurchaseRequestDao;
import domain.Apartment;
import domain.PurchaseRequest;

import java.sql.SQLException;
import java.util.List;

public class ApartmentService extends BaseService<Apartment> {

    public ApartmentService(ConnectionManager connectionManager) {
        super(connectionManager);
    }


    @Override
    public List<Apartment> getAll() throws SQLException {
        ApartmentDao apartmentDao = new ApartmentDao();
        transactionManager.initTransaction(apartmentDao);

        List<Apartment> apartments = apartmentDao.getAll();

        transactionManager.endTransaction();

        return apartments;
    }

    @Override
    public Apartment getById(int id) throws SQLException {
        if (id < 0) {
            throw new IllegalArgumentException("id can't be negative");
        }

        ApartmentDao apartmentDao = new ApartmentDao();
        transactionManager.initTransaction(apartmentDao);

        Apartment apartment = apartmentDao.findById(id);

        transactionManager.endTransaction();
        return apartment;
    }

    @Override
    public boolean create(Apartment entity) throws SQLException{
        if (entity == null) {
            throw new IllegalArgumentException("Apartment passed as argument is null");
        }

        ApartmentDao apartmentDao = new ApartmentDao();
        transactionManager.initTransaction(apartmentDao);

        boolean res = apartmentDao.create(entity);

        transactionManager.endTransaction();
        return res;
    }

    public List<PurchaseRequest> createWithCheck(Apartment entity) throws SQLException {
        if (entity == null) {
            throw new IllegalArgumentException("Apartment passed as argument is null");
        }

        ApartmentDao apartmentDao = new ApartmentDao();
        PurchaseRequestDao purchaseRequestDao = new PurchaseRequestDao();
        ClientDao clientDao = new ClientDao();
        transactionManager.initTransaction(apartmentDao, purchaseRequestDao, clientDao);

        List<PurchaseRequest> results = purchaseRequestDao.findByApartment(entity);
        if (results.isEmpty()) {
            apartmentDao.create(entity);
        }
        else {
            for (PurchaseRequest pr : results) {
                clientDao.loadClient(pr);
            }
        }

        transactionManager.endTransaction();
        return results;
    }

    @Override
    public boolean delete(int id) throws SQLException{
        if (id < 0) {
            throw new IllegalArgumentException("id can't be negative");
        }

        ApartmentDao apartmentDao = new ApartmentDao();
        transactionManager.initTransaction(apartmentDao);

        boolean res = apartmentDao.delete(id);

        transactionManager.endTransaction();
        return res;
    }

    @Override
    public boolean update(Apartment entity) throws SQLException{
        if (entity == null) {
            throw new IllegalArgumentException("Apartment passed as argument is null");
        }

        ApartmentDao apartmentDao = new ApartmentDao();
        transactionManager.initTransaction(apartmentDao);

        boolean res = (apartmentDao.update(entity) != null);

        transactionManager.endTransaction();
        return res;
    }
}

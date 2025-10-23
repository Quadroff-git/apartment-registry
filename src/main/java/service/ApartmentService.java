package service;

import db.ConnectionManager;
import db.dao.ApartmentDao;
import db.dao.ClientDao;
import db.dao.DaoException;
import db.dao.PurchaseRequestDao;
import domain.Apartment;
import domain.PurchaseRequest;

import java.util.List;

public class ApartmentService extends BaseService<Apartment> {

    public ApartmentService(ConnectionManager connectionManager) {
        super(connectionManager);
    }


    @Override
    public List<Apartment> getAll() throws ServiceException {
        ApartmentDao apartmentDao = new ApartmentDao();
        transactionManager.initTransaction(apartmentDao);

        List<Apartment> apartments = null;
        try {
            apartments = apartmentDao.getAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        transactionManager.endTransaction();

        return apartments;
    }

    @Override
    public Apartment getById(int id) throws ServiceException {
        if (id < 0) {
            throw new IllegalArgumentException("id can't be negative");
        }

        ApartmentDao apartmentDao = new ApartmentDao();
        transactionManager.initTransaction(apartmentDao);

        Apartment apartment = null;
        try {
            apartment = apartmentDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        transactionManager.endTransaction();
        return apartment;
    }

    @Override
    public boolean create(Apartment entity) throws ServiceException{
        if (entity == null) {
            throw new IllegalArgumentException("Apartment passed as argument is null");
        }

        ApartmentDao apartmentDao = new ApartmentDao();
        transactionManager.initTransaction(apartmentDao);
        try {
            boolean res = apartmentDao.create(entity);

            if (res) {
                transactionManager.commit();
            }
            transactionManager.endTransaction();

            return res;
        } catch (DaoException e) {
            transactionManager.rollback();
            transactionManager.endTransaction();
            throw new ServiceException(e);
        }

    }

    public List<PurchaseRequest> createWithCheck(Apartment entity) throws ServiceException {
        if (entity == null) {
            throw new IllegalArgumentException("Apartment passed as argument is null");
        }

        ApartmentDao apartmentDao = new ApartmentDao();
        PurchaseRequestDao purchaseRequestDao = new PurchaseRequestDao();
        ClientDao clientDao = new ClientDao();
        transactionManager.initTransaction(apartmentDao, purchaseRequestDao, clientDao);

        List<PurchaseRequest> results = null;
        try {
            results = purchaseRequestDao.findByApartment(entity);

            if (results.isEmpty()) {
                if(!apartmentDao.create(entity)) {
                    throw new ServiceException("Create failed");
                };
            }
            else {
                for (PurchaseRequest pr : results) {
                    clientDao.loadClient(pr);
                }
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        transactionManager.endTransaction();
        return results;
    }

    @Override
    public boolean delete(int id) throws ServiceException{
        if (id < 0) {
            throw new IllegalArgumentException("id can't be negative");
        }

        ApartmentDao apartmentDao = new ApartmentDao();
        transactionManager.initTransaction(apartmentDao);
        try {
            boolean res = apartmentDao.delete(id);

            if (res) {
                transactionManager.commit();
            }
            transactionManager.endTransaction();

            return res;
        } catch (DaoException e) {
            transactionManager.rollback();
            transactionManager.endTransaction();
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean update(Apartment entity) throws ServiceException{
        if (entity == null) {
            throw new IllegalArgumentException("Apartment passed as argument is null");
        }

        ApartmentDao apartmentDao = new ApartmentDao();
        transactionManager.initTransaction(apartmentDao);
        try {
            boolean res = (apartmentDao.update(entity) != null);

            if (res) {
                transactionManager.commit();
            }
            transactionManager.endTransaction();

            return res;
        } catch (DaoException e) {
            transactionManager.rollback();
            transactionManager.endTransaction();
            throw new ServiceException(e);
        }
    }
}

package service;

import db.ConnectionManager;
import db.dao.ClientDao;
import db.dao.DaoException;
import domain.Client;


import java.util.List;

public class ClientService extends BaseService<Client> {
    
    public ClientService(ConnectionManager connectionManager) {
        super(connectionManager);
    } 
    
    @Override
    public List<Client> getAll() throws ServiceException {
        ClientDao clientDao = new ClientDao();
        transactionManager.initTransaction(clientDao, clientDao);

        List<Client> clients = null;
        try {
            clients = clientDao.getAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        transactionManager.endTransaction();
        return clients;
    }

    @Override
    public Client getById(int id) throws ServiceException {
        if (id < 0) {
            throw new IllegalArgumentException("id can't be negative");
        }

        ClientDao ClientDao = new ClientDao();
        transactionManager.initTransaction(ClientDao);

        Client Client = null;
        try {
            Client = ClientDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        transactionManager.endTransaction();
        return Client;
    }

    @Override
    public boolean create(Client entity) throws ServiceException {
        if (entity == null) {
            throw new IllegalArgumentException("Client passed as argument is null");
        }

        ClientDao ClientDao = new ClientDao();
        transactionManager.initTransaction(ClientDao);
        try {
            boolean res = ClientDao.create(entity);

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
    public boolean delete(int id) throws ServiceException {
        if (id < 0) {
            throw new IllegalArgumentException("id can't be negative");
        }

        ClientDao ClientDao = new ClientDao();
        transactionManager.initTransaction(ClientDao);
        try {
            boolean res = ClientDao.delete(id);

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
    public boolean update(Client entity) throws ServiceException {
        if (entity == null) {
            throw new IllegalArgumentException("Client passed as argument is null");
        }

        ClientDao ClientDao = new ClientDao();
        transactionManager.initTransaction(ClientDao);
        try {
            boolean res = (ClientDao.update(entity) != null);

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

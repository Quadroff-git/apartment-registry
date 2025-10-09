package db.dao;

import domain.Client;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ClientDao extends BaseDao<Client> {
    public ClientDao(Connection connection) {
        super(connection);
    }


    @Override
    public List<Client> getAll() throws SQLException {
        return List.of();
    }

    @Override
    public Client findById(int id) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(Client entity) throws SQLException {
        return false;
    }

    @Override
    public boolean create(Client entity) throws SQLException {
        return false;
    }

    @Override
    public Client update(Client entity) throws SQLException {
        return null;
    }
}

package db.dao;


import domain.Client;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClientDao extends BaseDao<Client> {
    private static final String SQL_SELECT_ALL_CLIENTS =
            "SELECT * \n" +
            "FROM client c ";

    public ClientDao(Connection connection) {
        super(connection);
    }


    @Override
    public List<Client> getAll() throws SQLException {
        ArrayList<Client> clients = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_CLIENTS);

            while (resultSet.next()) {
                clients.add(getNextClient(resultSet));
            }
        }

        return clients;
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


    private Client getNextClient(ResultSet resultSet) throws SQLException {
        return new Client(
                resultSet.getInt("id"),
                resultSet.getString("Name"),
                resultSet.getString("phone")
        );
    }
}

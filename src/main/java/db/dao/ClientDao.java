package db.dao;


import domain.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDao extends BaseDao<Client> {
    private static final String SQL_SELECT_ALL_CLIENTS =
            "SELECT * \n" +
            "FROM client c ";

    private static final String SQL_FIND_CLIENT_BY_ID =
            "SELECT * \n" +
            "FROM client c \n" +
            "WHERE c.id = ?";

    private static final String SQL_DELETE_CLIENT =
            "DELETE \n" +
            "FROM client c\n" +
            "WHERE c.id = ?";


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
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_CLIENT_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return getNextClient(resultSet);
            }
            else {
                return null;
            }
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_CLIENT)) {
            preparedStatement.setInt(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();

            return rowsDeleted > 0;
        }
    }

    @Override
    public boolean delete(Client entity) throws SQLException {
        if (entity == null) {
            throw new NullPointerException("Client passed as argument is null");
        }

        if (entity.getId() == null) {
            throw new IllegalArgumentException("Client id = null");
        }

        return delete(entity.getId());
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

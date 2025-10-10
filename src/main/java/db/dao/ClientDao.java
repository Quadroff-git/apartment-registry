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

    private static final String SQL_INSERT_CLIENT =
            "INSERT INTO client\n" +
            "(name,\n" +
            "phone)\n" +
            "VALUES (?, ?)";

    private static final String SQL_UPDATE_CLIENT =
            "UPDATE client c\n" +
            "SET\n" +
            "name = ?,\n" +
            "phone = ?\n" +
            "WHERE\n" +
            "c.id = ?";


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
        if (entity == null) {
            throw new NullPointerException("Client passed as argument is null");
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_CLIENT, Statement.RETURN_GENERATED_KEYS)) {
            insertClient(preparedStatement, entity);

            int rowsAffected = preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                entity.setId(resultSet.getInt(1));
            }
            else {
                return false;
            }

            return rowsAffected > 0;
        }
    }

    @Override
    public Client update(Client entity) throws SQLException {
        if (entity == null) {
            throw new NullPointerException("Client passed as argument is null");
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_CLIENT)) {
            insertClient(preparedStatement, entity);
            preparedStatement.setInt(3, entity.getId());

            if (preparedStatement.executeUpdate() > 0) {
                return entity;
            }
            else {
                return null;
            }
        }
    }


    private static Client getNextClient(ResultSet resultSet) throws SQLException {
        return new Client(
                resultSet.getInt("id"),
                resultSet.getString("Name"),
                resultSet.getString("phone")
        );
    }

    private static void insertClient(PreparedStatement preparedStatement, Client client) throws SQLException {
        preparedStatement.setString(1, client.getFullName());
        preparedStatement.setString(2, client.getPhoneNumber());
    }
}

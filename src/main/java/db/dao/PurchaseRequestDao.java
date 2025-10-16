package db.dao;

import domain.Apartment;
import domain.Client;
import domain.PurchaseRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PurchaseRequestDao extends BaseDao<PurchaseRequest> {
    private static final String SQL_SELECT_ALL_PURCHASE_REQUESTS =
            "SELECT * \n" +
            "FROM purchase_request pr";

    private static final String SQL_FIND_PURCHASE_REQUEST_BY_ID =
            "SELECT * \n" +
            "FROM purchase_request pr \n" +
            "WHERE id = ?";

    private static final String SQL_DELETE_PURCHASE_REQUEST =
            "DELETE\n" +
            "FROM purchase_request \n" +
            "WHERE purchase_request.id = ?";

    private static final String SQL_INSERT_PURCHASE_REQUEST =
            "INSERT INTO purchase_request \n" +
            "(room_count,\n" +
            "min_area,\n" +
            "max_area,\n" +
            "min_price,\n" +
            "max_price,\n" +
            "client_id)\n" +
            "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE_PURCHASE_REQUEST =
            "UPDATE purchase_request pr \n" +
            "SET\n" +
            "room_count = ?,\n" +
            "min_area = ?,\n" +
            "max_area = ?,\n" +
            "min_price = ?,\n" +
            "max_price = ?,\n" +
            "client_id = ?\n" +
            "WHERE pr.id = ?";

    private static final String SQL_FIND_PURCHASE_REQUEST_BY_APARTMENT =
            "SELECT *\n" +
            "FROM purchase_request pr\n" +
            "WHERE \n" +
            "pr.room_count = ?\n" +
            "AND (? BETWEEN pr.min_area  AND pr.max_area) \n" +
            "AND (? BETWEEN pr.min_price AND pr.max_price)";


    @Override
    public List<PurchaseRequest> getAll() throws SQLException {
        ArrayList<PurchaseRequest> purchaseRequests = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_PURCHASE_REQUESTS);

            while (resultSet.next()) {
                purchaseRequests.add(getNextPurchaseRequest(resultSet));
            }
        }

        return purchaseRequests;
    }

    @Override
    public PurchaseRequest findById(int id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_PURCHASE_REQUEST_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return getNextPurchaseRequest(resultSet);
            }
            else {
                return null;
            }
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_PURCHASE_REQUEST)) {
            preparedStatement.setInt(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();

            return rowsDeleted > 0;
        }
    }

    @Override
    public boolean delete(PurchaseRequest entity) throws SQLException {
        if (entity == null) {
            throw new IllegalArgumentException("Purchase request passed as argument is null");
        }

        if (entity.getId() == null) {
            throw new IllegalArgumentException("Purchase request id = null");
        }

        return delete(entity.getId());
    }

    @Override
    public boolean create(PurchaseRequest entity) throws SQLException {
        if (entity == null) {
            throw new IllegalArgumentException("Purchase request passed as argument is null");
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_PURCHASE_REQUEST, Statement.RETURN_GENERATED_KEYS)) {
            insertPurchaseRequest(preparedStatement, entity);

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
    public PurchaseRequest update(PurchaseRequest entity) throws SQLException {
        if (entity == null) {
            throw new IllegalArgumentException("Purchase request passed as argument is null");
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_PURCHASE_REQUEST)) {
            insertPurchaseRequest(preparedStatement, entity);
            preparedStatement.setInt(7, entity.getId());

            if (preparedStatement.executeUpdate() > 0) {
                return entity;
            }
            else {
                return null;
            }
        }
    }

    public List<PurchaseRequest> findByApartment(Apartment apartment) throws SQLException {
        if (apartment == null) {
            throw new IllegalArgumentException("Apartment passed as argument is null");
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_PURCHASE_REQUEST_BY_APARTMENT)) {
            preparedStatement.setInt(1, apartment.getRoomCount());
            preparedStatement.setInt(2, apartment.getArea());
            preparedStatement.setInt(3, apartment.getPrice());

            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<PurchaseRequest> purchaseRequests = new ArrayList<>();
            while (resultSet.next()) {
                purchaseRequests.add(getNextPurchaseRequest(resultSet));
            }

            return purchaseRequests;
        }
    }


    // Assumes the resultSet is on a valid row(next() must be called at least once)
    private static PurchaseRequest getNextPurchaseRequest(ResultSet resultSet) throws SQLException {
        return new PurchaseRequest(
                resultSet.getInt("id"),
                resultSet.getInt("room_count"),
                resultSet.getInt("min_area"),
                resultSet.getInt("max_area"),
                resultSet.getInt("min_price"),
                resultSet.getInt("max_price"),
                // Lazy loading approach:
                // right after loading from db client field contains a stub Client object. Its id is correct,
                // but to actually get full info it has to be loaded from db manually using ClientDao
                new Client(resultSet.getInt("client_id"), null, null)
        );
    }

    // Assumes the columns and placeholders for their values are in a specific order
    private static void insertPurchaseRequest(PreparedStatement preparedStatement, PurchaseRequest purchaseRequest) throws SQLException {
        if (purchaseRequest.getClient().getId() == null) {
            throw new IllegalArgumentException("Client id = null. Insert client into db first");
        }

        preparedStatement.setInt(1, purchaseRequest.getRoomCount());
        preparedStatement.setInt(2, purchaseRequest.getMinArea());
        preparedStatement.setInt(3, purchaseRequest.getMaxArea());
        preparedStatement.setInt(4, purchaseRequest.getMinPrice());
        preparedStatement.setInt(5, purchaseRequest.getMaxPrice());
        preparedStatement.setInt(6, purchaseRequest.getClient().getId());
    }
}

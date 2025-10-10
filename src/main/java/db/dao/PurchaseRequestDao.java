package db.dao;

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


    public PurchaseRequestDao(Connection connection) {
        super(connection);
    }


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
            throw new NullPointerException("Purchase request passed as argument is null");
        }

        if (entity.getId() == null) {
            throw new IllegalArgumentException("Purchase request id = null");
        }

        return delete(entity.getId());
    }

    @Override
    public boolean create(PurchaseRequest entity) throws SQLException {
        return false;
    }

    @Override
    public PurchaseRequest update(PurchaseRequest entity) throws SQLException {
        return null;
    }


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
                new Client(resultSet.getInt("client_id"), "awaiting loading the full client info", "client id field is correct")
        );
    }
}

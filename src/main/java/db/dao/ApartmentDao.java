package db.dao;

import db.ConnectionManager;
import domain.Apartment;
import domain.PurchaseRequest;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApartmentDao extends BaseDao<Apartment>{
    private static final String SQL_SELECT_ALL_APARTMENTS =
            "SELECT * \n" +
            "FROM apartment";

    private static final String SQL_FIND_APARTMENT_BY_ID =
            "SELECT * \n" +
            "FROM apartment\n" +
            "WHERE apartment.id = ?";

    private static final String SQL_DELETE_APARTMENT =
            "DELETE\n" +
            "FROM apartment\n" +
            "WHERE apartment.id = ?";

    private static final String SQL_INSERT_APARTMENT =
            "INSERT INTO apartment \n" +
            "(room_count, \n" +
            "area, \n" +
            "price, \n" +
            "street, \n" +
            "building, \n" +
            "number)\n" +
            "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE_APARTMENT =
            "UPDATE apartment\n" +
            "SET \n" +
            "room_count = ?,\n" +
            "area = ?,\n" +
            "price = ?,\n" +
            "street = ?,\n" +
            "building = ?,\n" +
            "number = ?\n" +
            "WHERE\n" +
            "apartment.id = ?";

    private static final String SQL_FIND_APARTMENT_BY_PURCHASE_REQUEST =
            "SELECT * \n" +
            "FROM apartment a\n" +
            "WHERE \n" +
            "a.room_count = ?\n" +
            "AND (a.price BETWEEN ? AND ?)\n" +
            "AND (a.area BETWEEN ? AND ?)\n";



    public ApartmentDao(Connection connection) {
        super(connection);
    }


    @Override
    public List<Apartment> getAll() throws SQLException {
        ArrayList<Apartment> apartments = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_APARTMENTS);

            while (resultSet.next()) {
                apartments.add(getNextApartment(resultSet));
            }
        }

        return apartments;
    }

    @Override
    public Apartment findById(int id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_APARTMENT_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return getNextApartment(resultSet);
            }
            else {
                return null;
            }
        }

    }

    @Override
    public boolean delete(int id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_APARTMENT)) {
            preparedStatement.setInt(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();

            return rowsDeleted > 0;
        }
    }

    @Override
    public boolean delete(Apartment entity) throws SQLException{
        if (entity.getId() == null) {
            throw new IllegalArgumentException("Apartment id = null");
        }

        return delete(entity.getId());
    }

    @Override
    public boolean create(Apartment entity) throws SQLException{
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_APARTMENT, Statement.RETURN_GENERATED_KEYS)) {
            insertApartment(preparedStatement, entity);

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
    public Apartment update(Apartment entity) throws SQLException{
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_APARTMENT)) {
            insertApartment(preparedStatement, entity);

            if (preparedStatement.executeUpdate() > 0) {
                return entity;
            }
            else {
                return null;
            }
        }
    }

    public List<Apartment> findByPurchaseRequest(PurchaseRequest purchaseRequest) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_APARTMENT_BY_PURCHASE_REQUEST)) {
            preparedStatement.setInt(1, purchaseRequest.getRoomCount());
            preparedStatement.setInt(2, purchaseRequest.getMinPrice());
            preparedStatement.setInt(3, purchaseRequest.getMaxPrice());
            preparedStatement.setInt(4, purchaseRequest.getMinArea());
            preparedStatement.setInt(5, purchaseRequest.getMaxArea());

            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Apartment> apartments = new ArrayList<>();
            while (resultSet.next()) {
                apartments.add(getNextApartment(resultSet));
            }

            return apartments;
        }
    }


    // Accepts a resultSet with cursor set to a non-empty row
    private static Apartment getNextApartment(ResultSet resultSet) throws SQLException {
        return new Apartment(
                resultSet.getInt("id"),
                resultSet.getString("street"),
                resultSet.getInt("building"),
                resultSet.getInt("number"),
                resultSet.getInt("room_count"),
                resultSet.getInt("area"),
                resultSet.getInt("price"));
    }

    private static void insertApartment(PreparedStatement preparedStatement, Apartment entity) throws SQLException {
        preparedStatement.setInt(1, entity.getRoomCount());
        preparedStatement.setInt(2, entity.getArea());
        preparedStatement.setInt(3, entity.getPrice());
        preparedStatement.setString(4, entity.getStreet());
        preparedStatement.setInt(5, entity.getBuilding());
        preparedStatement.setInt(6, entity.getNumber());
        preparedStatement.setInt(7, entity.getId());
    }


    public static void main(String[] argv) {
        try {
            ConnectionManager conn = new ConnectionManager(argv[0], argv[1], argv[2]);

            ApartmentDao apartmentDao = new ApartmentDao(conn.getConnection());

            PurchaseRequest pr = new PurchaseRequest(2, 40, 60, 6000000, 9000000, null);

            List<Apartment> list = apartmentDao.findByPurchaseRequest(pr);

            for (Apartment a : list) {
                System.out.println(a + "\n");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }


    }
}

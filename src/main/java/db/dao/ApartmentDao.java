package db.dao;

import db.ConnectionManager;
import domain.Apartment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ApartmentDao extends BaseDao<Apartment>{
    private static final String SQL_SELECT_ALL_APARTMENTS =
            "SELECT * \n" +
            "FROM apartment";

    private static final String SQL_FIND_APARTMENT_BY_ID =
            "SELECT *\n" +
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
            "number, \n" +
            "client_id) \n" +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE_APARTMENT =
            "UPDATE apartment\n" +
            "SET \n" +
            "apartment.room_count = ?\n" +
            "apartment.area = ?\n" +
            "apartment.price = ?\n" +
            "apartment.street = ?\n" +
            "apartment.building = ?\n" +
            "apartment.number = ?\n" +
            "WHERE\n" +
            "apartment.id = ?";


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
    public Apartment findById(int id) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean delete(Apartment entity) {
        return false;
    }

    @Override
    public boolean create(Apartment entity) {
        return false;
    }

    @Override
    public Apartment update(Apartment entity) {
        return null;
    }


    private Apartment getNextApartment(ResultSet resultSet) throws SQLException{
        return new Apartment(
                resultSet.getInt("id"),
                resultSet.getString("street") + " " + resultSet.getInt("building") + ", " + resultSet.getInt("number"),
                resultSet.getInt("room_count"),
                resultSet.getInt("area"),
                resultSet.getInt("price"));
    }


    public static void main(String[] argv) {
        try {
            ConnectionManager conn = new ConnectionManager(argv[0], argv[1], argv[2]);

            ApartmentDao apartmentDao = new ApartmentDao(conn.getConnection());
            for (Apartment apartment : apartmentDao.getAll()) {
                System.out.println(apartment + "\n");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }


    }
}

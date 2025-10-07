package db.dao;

import domain.Apartment;

import java.sql.Connection;
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
    public List<Apartment> getAll() {
        return List.of();
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
}

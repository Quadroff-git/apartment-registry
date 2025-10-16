import db.ConnectionManager;
import db.dao.ClientDao;
import db.dao.PurchaseRequestDao;
import db.dao.TransactionManager;
import domain.Apartment;
import domain.Client;
import domain.PurchaseRequest;
import service.ApartmentService;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] argv) {
        try {
            ConnectionManager cm = new ConnectionManager(argv[0], argv[1], argv[2]);
            ApartmentService as = new ApartmentService(cm);
            System.out.println(as.getById(16));


        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}

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
            Apartment a = new Apartment("street", 1, 1, 2, 67, 9100000);
            var res = as.addWithCheck(a);
            res.forEach(pr -> System.out.println(pr + "\n"));

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}

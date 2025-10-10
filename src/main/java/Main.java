import db.ConnectionManager;
import db.dao.ClientDao;
import db.dao.PurchaseRequestDao;
import domain.Apartment;
import domain.Client;
import domain.PurchaseRequest;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] argv) {
        try {
            ConnectionManager connectionManager = new ConnectionManager(argv[0], argv[1], argv[2]);
            PurchaseRequestDao purchaseRequestDao = new PurchaseRequestDao(connectionManager.getConnection());

            Apartment a = new Apartment("", 1, 1, 2, 72, 8000000);

            List<PurchaseRequest> pr = purchaseRequestDao.findByApartment(a);
            for (PurchaseRequest p : pr) {
                System.out.println(p + "\n");
            }



        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}

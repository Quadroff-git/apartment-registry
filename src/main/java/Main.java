import db.ConnectionManager;
import db.dao.ClientDao;
import db.dao.PurchaseRequestDao;
import domain.Client;
import domain.PurchaseRequest;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] argv) {
        try {
            ConnectionManager connectionManager = new ConnectionManager(argv[0], argv[1], argv[2]);
            PurchaseRequestDao purchaseRequestDao = new PurchaseRequestDao(connectionManager.getConnection());


            System.out.println(purchaseRequestDao.findById(6));
//            List<PurchaseRequest> purchaseRequests = purchaseRequestDao.getAll();
//
//            for (PurchaseRequest pr : purchaseRequests) {
//                System.out.println(pr + "\n");
//            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}

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
            ClientDao clientDao = new ClientDao(connectionManager.getConnection());

            PurchaseRequest pr = new PurchaseRequest(11, 2, 69, 420, 69000, 4200000, clientDao.findById(1));
            purchaseRequestDao.delete(pr);

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}

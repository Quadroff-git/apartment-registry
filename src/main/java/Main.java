import db.ConnectionManager;
import domain.Apartment;
import domain.PurchaseRequest;
import service.ApartmentService;
import service.PurchaseRequestService;

import java.sql.SQLException;

public class Main {
    public static void main(String[] argv) {
        try {
            ConnectionManager cm = new ConnectionManager(argv[0], argv[1], argv[2]);
            PurchaseRequestService prs = new PurchaseRequestService(cm);
            PurchaseRequest pr = prs.getById(10);
            pr.setRoomCount(1);
            System.out.println(prs.update(pr));

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}

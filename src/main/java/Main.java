import db.ConnectionManager;
import domain.Apartment;
import domain.PurchaseRequest;
import presentation.GUI;
import service.ApartmentService;
import service.PurchaseRequestService;

import java.sql.SQLException;

public class Main {
    public static void main(String[] argv) {
        try {
            ConnectionManager cm = new ConnectionManager(argv[0], argv[1], argv[2]);
            GUI gui = new GUI(cm);
            gui.start();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

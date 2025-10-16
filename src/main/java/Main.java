import db.ConnectionManager;
import domain.Apartment;
import service.ApartmentService;

import java.sql.SQLException;

public class Main {
    public static void main(String[] argv) {
        try {
            ConnectionManager cm = new ConnectionManager(argv[0], argv[1], argv[2]);
            ApartmentService as = new ApartmentService(cm);
            Apartment a = new Apartment("street", 1, 1, 2, 67, 9100000);
            var res = as.createWithCheck(a);
            res.forEach(pr -> System.out.println(pr + "\n"));

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}

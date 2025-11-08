import db.ConnectionManager;
import db.TransactionManager;
import domain.Apartment;
import domain.PurchaseRequest;
import org.junit.jupiter.api.Test;
import presentation.GUI;
import service.ApartmentService;
import service.ServiceException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RiatTests {
    private ConnectionManager cm = new ConnectionManager("jdbc:postgresql:apartment_registry", "postgres", "root");
    private final ApartmentService as = new ApartmentService(cm);

    @Test
    void createWithCheck1() {
        assertThrows(IllegalArgumentException.class,() -> as.createWithCheck(null));
    }

    @Test
    void createWithCheck2() {
        Apartment entity = new Apartment("", 1, 1, 1, 32, 5000000);
        try {
            List<PurchaseRequest> results = as.createWithCheck(entity);
            assertEquals(1, results.size());
            assertEquals(7, results.get(0).getId());
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void createWithCheck3() {
        Apartment entity = new Apartment("", 1, 1, 5, 170, 10000000);
        assertThrows(ServiceException.class, () -> as.createWithCheck(entity));
    }

    @Test
    void createWithCheck4() {
        Apartment entity = new Apartment("", 1, 1, 5, 170, 10000000);
        try {
            List<PurchaseRequest> results = as.createWithCheck(entity);
            assertTrue(results.isEmpty());
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
}

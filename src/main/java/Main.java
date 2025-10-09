import db.ConnectionManager;
import db.dao.ClientDao;
import domain.Client;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] argv) {
        try {
            ConnectionManager connectionManager = new ConnectionManager(argv[0], argv[1], argv[2]);
            ClientDao clientDao = new ClientDao(connectionManager.getConnection());

            List<Client> clients = clientDao.getAll();

            for (Client client : clients) {
                System.out.println(client + "\n");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}

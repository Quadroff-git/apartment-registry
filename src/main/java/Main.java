import db.ConnectionManager;
import presentation.GUI;

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

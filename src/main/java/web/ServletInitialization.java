package web;

import db.ConnectionManager;
import service.ApartmentService;
import service.ClientService;
import service.PurchaseRequestService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ServletInitialization implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String url = System.getProperty("db.url");
        String username = System.getProperty("db.username");
        String password = System.getProperty("db.password");

        ConnectionManager connectionManager = new ConnectionManager(url, username, password);

        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("apartmentService", new ApartmentService(connectionManager));
        servletContext.setAttribute("purchaseRequestService", new PurchaseRequestService(connectionManager));
        servletContext.setAttribute("clientService", new ClientService(connectionManager));
    }
}

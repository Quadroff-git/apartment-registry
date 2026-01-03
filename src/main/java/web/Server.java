package web;

import db.ConnectionManager;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import service.ApartmentService;
import service.ClientService;
import service.PurchaseRequestService;
import web.servlet.ApartmentServlet;
import web.servlet.ClientServlet;
import web.servlet.PurchaseRequestServlet;

import java.io.File;

public class Server {
    public static void main(String[] args) {
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir("temp");
        tomcat.setPort(8080);

        String contextPath = "/api";
        String docBase = new File(".").getAbsolutePath();

        Context context = tomcat.addContext(contextPath, docBase);

        ConnectionManager connectionManager = new ConnectionManager(args[0], args[1], args[2]);
        ApartmentService apartmentService = new ApartmentService(connectionManager);
        ClientService clientService = new ClientService(connectionManager);
        PurchaseRequestService purchaseRequestService = new PurchaseRequestService(connectionManager);

        ApartmentServlet apartmentServlet = new ApartmentServlet(apartmentService);
        tomcat.addServlet(contextPath, "ApartmentServlet", apartmentServlet);
        context.addServletMappingDecoded("/apartment", "ApartmentServlet");

        ClientServlet clientServlet = new ClientServlet(clientService);
        tomcat.addServlet(contextPath, "ClientServlet", clientServlet);
        context.addServletMappingDecoded("/client", "ClientServlet");

        PurchaseRequestServlet purchaseRequestServlet = new PurchaseRequestServlet(purchaseRequestService);
        tomcat.addServlet(contextPath, "PurchaseRequestServlet", purchaseRequestServlet);
        context.addServletMappingDecoded("/purchase-request", "PurchaseRequestServlet");

        try {
            tomcat.start();
        } catch (LifecycleException e) {
            System.out.println(e);
        }
        System.out.println("server waiting at http://localhost:8080/api/");
        tomcat.getServer().await();
    }

}

package web;

import db.ConnectionManager;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import service.ApartmentService;
import web.servlet.ApartmentServlet;

import javax.servlet.ServletException;
import java.io.File;

public class Server {
    public static void main(String[] args) throws LifecycleException {
        ConnectionManager connectionManager = new ConnectionManager(args[0], args[1], args[2]);
        ApartmentService apartmentService = new ApartmentService(connectionManager);
        ApartmentServlet apartmentServlet = new ApartmentServlet(apartmentService);

        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir("temp");
        tomcat.setPort(8080);

        String contextPath = "/api";
        String docBase = new File(".").getAbsolutePath();

        Context context = tomcat.addContext(contextPath, docBase);


        String servletName = "ApartmentServlet";
        String urlPattern = "/apartment";
        tomcat.addServlet(contextPath, servletName, apartmentServlet);
        context.addServletMappingDecoded(urlPattern, servletName);

        tomcat.start();
        System.out.println("server waiting at http://localhost:8080/api/");
        tomcat.getServer().await();
    }

}

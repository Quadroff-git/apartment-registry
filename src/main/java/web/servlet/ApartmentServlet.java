package web.servlet;

import domain.Apartment;
import service.ApartmentService;
import service.ServiceException;
import tools.jackson.jr.ob.JSON;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/apartment")
public class ApartmentServlet extends HttpServlet {
    private ApartmentService apartmentService;

    @Override
    public void init() {
        this.apartmentService = (ApartmentService) getServletContext().getAttribute("apartmentService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            List<Apartment> apartments = apartmentService.getAll();
            response.setContentType("application/json");
            PrintWriter writer = response.getWriter();
            JSON.std.write(apartments, writer);
        } catch (ServiceException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(JSON.std.asString(e));
        }
    }
}

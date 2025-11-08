package web.servlet;

import domain.Apartment;
import domain.PurchaseRequest;
import service.ApartmentService;
import service.ServiceException;
import tools.jackson.jr.ob.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.List;

@WebServlet("/apartment")
public class ApartmentServlet extends HttpServlet {
    private ApartmentService apartmentService;

    public ApartmentServlet(ApartmentService apartmentService) {
        super();
        this.apartmentService = apartmentService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            List<Apartment> apartments = apartmentService.getAll();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            JSON.std.write(apartments, writer);
        } catch (ServiceException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(JSON.std.asString(e));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        if (!validateBody(request, response)) {
            return;
        }

        Apartment apartment = JSON.std.beanFrom(Apartment.class, request.getReader());

        List<PurchaseRequest> purchaseRequests = null;
        try {
             purchaseRequests = apartmentService.createWithCheck(apartment);

            if (purchaseRequests.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_CREATED);
            }
            else {
                response.setStatus(HttpServletResponse.SC_OK);
                JSON.std.write(purchaseRequests, response.getWriter());
            }
        } catch (ServiceException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(JSON.std.asString(e));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Apartment apartment = JSON.std.beanFrom(Apartment.class, request.getReader());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        if (!validateBody(request, response)) {
            return;
        }

        try {
            boolean result = apartmentService.update(apartment);

            if (result) {
                response.setStatus(HttpServletResponse.SC_OK);
            }
            else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (ServiceException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(JSON.std.asString(e));
        }
    }

    private boolean validateBody(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Reader reader = request.getReader();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        if (!reader.ready()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JSON.std.write("No entity supplied", response.getWriter());
            return false;
        }

        return true;
    }
}

package web.servlet;

import domain.Apartment;
import domain.PurchaseRequest;
import service.ApartmentService;
import service.ServiceException;
import tools.jackson.jr.ob.JSON;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.List;

@WebServlet("/apartment")
// TODO: Create AbstractServlet for sevlets to inherit
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

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        String requestBody = getRequestBody(request);
        if (requestBody == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(JSON.std.asString("The request body is empty!"));
            return;
        }

        Apartment apartment = JSON.std.beanFrom(Apartment.class, requestBody);

        List<PurchaseRequest> purchaseRequests = null;
        try {
             purchaseRequests = apartmentService.createWithCheck(apartment);

            if (purchaseRequests.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_CREATED);
            }
            else {
                response.setStatus(HttpServletResponse.SC_OK);
                //JSON.std.write(purchaseRequests, response.getWriter());
                response.getWriter().write(JSON.std.asString(purchaseRequests));
            }
        } catch (ServiceException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(JSON.std.asString(e));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        String requestBody = getRequestBody(request);
        if (requestBody == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(JSON.std.asString("The request body is empty!"));
            return;
        }

        Apartment apartment = JSON.std.beanFrom(Apartment.class, requestBody);

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

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        String idString = request.getParameter("id");

        if (idString == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(JSON.std.asString("Supply id of the apartment to delete as a parameter"));
            return;
        }

        Integer id = null;
        try {
            id = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(JSON.std.asString("Id must be a number"));
            return;
        }

        try {
            boolean result = apartmentService.delete(id);

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

    private String getRequestBody(HttpServletRequest request) throws IOException {
        try (Reader reader = request.getReader()) {
            if (!reader.ready()) {
                return null;
            }

            BufferedReader  bufferedReader = new BufferedReader(reader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(System.lineSeparator());
            }

            return stringBuilder.toString();

        }
    }
}

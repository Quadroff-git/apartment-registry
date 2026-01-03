package web.servlet;

import domain.Apartment;
import domain.PurchaseRequest;
import service.PurchaseRequestService;
import service.ServiceException;
import tools.jackson.jr.ob.JSON;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class PurchaseRequestServlet extends BaseServlet {
    private final PurchaseRequestService purchaseRequestService;

    public PurchaseRequestServlet(PurchaseRequestService purchaseRequestService) {
        super();
        this.purchaseRequestService = purchaseRequestService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            List<PurchaseRequest> purchaseRequests = purchaseRequestService.getAll();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            JSON.std.write(purchaseRequests, writer);
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

        PurchaseRequest purchaseRequest = JSON.std.beanFrom(PurchaseRequest.class, requestBody);

        List<Apartment> apartments = null;
        try {
            apartments = purchaseRequestService.createWithCheck(purchaseRequest);

            if (apartments.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_CREATED);
            }
            else {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(JSON.std.asString(apartments));
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

        PurchaseRequest purchaseRequest = JSON.std.beanFrom(PurchaseRequest.class, requestBody);

        try {
            boolean result = purchaseRequestService.update(purchaseRequest);

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
            boolean result = purchaseRequestService.delete(id);

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
}

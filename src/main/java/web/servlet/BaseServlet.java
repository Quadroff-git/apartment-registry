package web.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public abstract class BaseServlet extends HttpServlet {
    @Override
    protected abstract void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException;

    @Override
    protected abstract void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException;

    @Override
    protected abstract void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException;

    @Override
    protected abstract void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException;

    protected String getRequestBody(HttpServletRequest request) throws IOException {
        try (Reader reader = request.getReader()) {
            if (!reader.ready()) {
                return null;
            }

            BufferedReader bufferedReader = new BufferedReader(reader);
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

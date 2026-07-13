package api.handler;

import api.repository.StudentRepository;
import api.util.JsonUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class StudentHandler implements HttpHandler {

    private final StudentRepository repository = new StudentRepository();

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String method = exchange.getRequestMethod();

        if (method.equals("GET")) {

            String json = JsonUtil.getMapper()
                    .writeValueAsString(repository.findAll());

            exchange.getResponseHeaders().add(
                    "Content-Type",
                    "application/json"
            );

            exchange.sendResponseHeaders(200, json.getBytes().length);

            OutputStream output = exchange.getResponseBody();

            output.write(json.getBytes());

            output.close();

            return;
        }

        exchange.sendResponseHeaders(405, -1);

    }

}

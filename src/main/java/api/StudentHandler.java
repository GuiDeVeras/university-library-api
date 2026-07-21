package api;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class StudentHandler implements HttpHandler {

    private final StudentRepository repository = new StudentRepository();

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        switch (exchange.getRequestMethod()) {

            case "GET":
                handleGet(exchange);
                break;

            case "POST":
                handlePost(exchange);
                break;

            case "PUT":
                handlePut(exchange);
                break;

            case "DELETE":
                handleDelete(exchange);
                break;

            default:
                sendResponse(
                        exchange,
                        405,
                        "{\"message\":\"Method Not Allowed\"}"
                );

        }

    }

    private void handleGet(HttpExchange exchange) throws IOException {

        String path = exchange.getRequestURI().getPath();

        // GET /students
        if (path.equals("/students")) {

            String json = JsonUtil.getMapper()
                    .writeValueAsString(repository.findAll());

            sendResponse(exchange, 200, json);

            return;
        }

        // GET /students/{id}

        String[] parts = path.split("/");

        if (parts.length != 3) {

            sendResponse(exchange, 400,
                    "{\"message\":\"Invalid URL\"}");

            return;

        }

        try {

            int id = Integer.parseInt(parts[2]);

            Student student = repository.findById(id);

            if (student == null) {

                sendResponse(exchange, 404,
                        "{\"message\":\"Student not found\"}");

                return;

            }

            String json = JsonUtil.getMapper()
                    .writeValueAsString(student);

            sendResponse(exchange, 200, json);

        } catch (NumberFormatException exception) {

            sendResponse(exchange, 400,
                    "{\"message\":\"Invalid id\"}");

        }

    }

    private void handlePost(HttpExchange exchange)
            throws IOException {

        String body = new String(
                exchange.getRequestBody().readAllBytes()
        );

        Student student = JsonUtil.getMapper()
                .readValue(body, Student.class);

        Student createdStudent = repository.create(student);

        String json = JsonUtil.getMapper()
                .writeValueAsString(createdStudent);

        sendResponse(exchange, 201, json);

    }

    private void handlePut(HttpExchange exchange)
            throws IOException {

        String[] parts = exchange.getRequestURI()
                .getPath()
                .split("/");

        if (parts.length != 3) {

            sendResponse(exchange, 400,
                    "{\"message\":\"Invalid URL\"}");

            return;

        }

        try {

            int id = Integer.parseInt(parts[2]);

            Student updatedStudent = JsonUtil.getMapper()
                    .readValue(
                            exchange.getRequestBody().readAllBytes(),
                            Student.class
                    );

            Student student = repository.update(id, updatedStudent);

            if (student == null) {

                sendResponse(exchange, 404,
                        "{\"message\":\"Student not found\"}");

                return;

            }

            String json = JsonUtil.getMapper()
                    .writeValueAsString(student);

            sendResponse(exchange, 200, json);

        } catch (NumberFormatException exception) {

            sendResponse(exchange, 400,
                    "{\"message\":\"Invalid id\"}");

        }

    }

    private void handleDelete(HttpExchange exchange)
            throws IOException {

        String[] parts = exchange.getRequestURI()
                .getPath()
                .split("/");

        if (parts.length != 3) {

            sendResponse(exchange, 400,
                    "{\"message\":\"Invalid URL\"}");

            return;

        }

        try {

            int id = Integer.parseInt(parts[2]);

            boolean deleted = repository.delete(id);

            if (!deleted) {

                sendResponse(exchange, 404,
                        "{\"message\":\"Student not found\"}");

                return;

            }

            exchange.sendResponseHeaders(204, -1);
            exchange.close();

        } catch (NumberFormatException exception) {

            sendResponse(exchange, 400,
                    "{\"message\":\"Invalid id\"}");

        }

    }

    private void sendResponse(
            HttpExchange exchange,
            int statusCode,
            String response
    ) throws IOException {

        exchange.getResponseHeaders()
                .set("Content-Type", "application/json");

        exchange.sendResponseHeaders(
                statusCode,
                response.getBytes().length
        );

        try (OutputStream output = exchange.getResponseBody()) {

            output.write(response.getBytes());

        }

    }

}

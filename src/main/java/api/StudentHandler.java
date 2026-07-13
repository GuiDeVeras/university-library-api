package api;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class StudentHandler implements HttpHandler {

    private final List<Student> students = new ArrayList<>();

    private int nextId = 1;

    public StudentHandler() {

        students.add(new Student(
                nextId++,
                "Lucas Almeida",
                "lucas@email.com",
                "20250001",
                "Computer Science"
        ));

        students.add(new Student(
                nextId++,
                "Emma Johnson",
                "emma@email.com",
                "20250002",
                "Software Engineering"
        ));

    }

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
                    .writeValueAsString(students);

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

            for (Student student : students) {

                if (student.getId() == id) {

                    String json = JsonUtil.getMapper()
                            .writeValueAsString(student);

                    sendResponse(exchange, 200, json);

                    return;

                }

            }

            sendResponse(exchange, 404,
                    "{\"message\":\"Student not found\"}");

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

        student.setId(nextId++);

        students.add(student);

        String json = JsonUtil.getMapper()
                .writeValueAsString(student);

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

            for (Student student : students) {

                if (student.getId() == id) {

                    student.setName(updatedStudent.getName());
                    student.setEmail(updatedStudent.getEmail());
                    student.setRegistration(updatedStudent.getRegistration());
                    student.setCourse(updatedStudent.getCourse());

                    String json = JsonUtil.getMapper()
                            .writeValueAsString(student);

                    sendResponse(exchange, 200, json);

                    return;

                }

            }

            sendResponse(exchange, 404,
                    "{\"message\":\"Student not found\"}");

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

            for (Student student : students) {

                if (student.getId() == id) {

                    students.remove(student);

                    exchange.sendResponseHeaders(204, -1);

                    exchange.close();

                    return;

                }

            }

            sendResponse(exchange, 404,
                    "{\"message\":\"Student not found\"}");

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

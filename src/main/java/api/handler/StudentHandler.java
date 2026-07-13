package api.handler;

import api.repository.StudentRepository;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class StudentHandler implements HttpHandler {

    private final StudentRepository repository = new StudentRepository();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
    
    	String method = exchange.getRequestMethod();

    	System.out.println(method);

    }

}

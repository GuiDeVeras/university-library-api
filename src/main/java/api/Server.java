package api;

import com.sun.net.httpserver.HttpServer;
import api.handler.StudentHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

	server.createContext("/students", new StudentHandler());

        server.start();

        System.out.println("Server started at http://localhost:8080");

    }

}

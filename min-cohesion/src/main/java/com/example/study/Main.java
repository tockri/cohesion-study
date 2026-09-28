package com.example.study;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    record Todo(int id, String title) {
    }

    static List<Todo> todos = new ArrayList<>() {
        {
            add(new Todo(1, "犬の散歩"));
            add(new Todo(2, "夕飯の買い物"));
        }
    };

    static AtomicInteger nextId = new AtomicInteger(3);

    static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/", exchange -> {
            String help = "GET  /todos  Todoの一覧を取得する\nPOST /todos  Todoを追加する\n";
            byte[] body = help.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "text/plain; charset=utf-8");
            exchange.sendResponseHeaders(200, body.length);
            OutputStream os = exchange.getResponseBody();
            os.write(body);
            os.close();
            exchange.close();
        });

        server.createContext("/todos", exchange -> {
            String method = exchange.getRequestMethod();

            if (method.equals("GET")) {
                byte[] body = mapper.writeValueAsBytes(todos);
                exchange.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");
                exchange.sendResponseHeaders(200, body.length);
                OutputStream os = exchange.getResponseBody();
                os.write(body);
                os.close();

            } else if (method.equals("POST")) {
                byte[] requestBytes = exchange.getRequestBody().readAllBytes();
                Map<?, ?> request = mapper.readValue(requestBytes, Map.class);
                String title = String.valueOf(request.get("title"));

                Todo todo = new Todo(nextId.getAndIncrement(), title);
                todos.add(todo);

                byte[] body = mapper.writeValueAsBytes(todo);
                exchange.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");
                exchange.sendResponseHeaders(201, body.length);
                OutputStream os = exchange.getResponseBody();
                os.write(body);
                os.close();

            } else {
                exchange.sendResponseHeaders(405, -1);
            }

            exchange.close();
        });

        server.start();
        System.out.println("Server started: http://localhost:8080/");
    }
}

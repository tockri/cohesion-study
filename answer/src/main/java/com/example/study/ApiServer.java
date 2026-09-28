package com.example.study;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

public class ApiServer {
    private final ObjectMapper mapper = new ObjectMapper();

    void start(int port, Collection<Route> routes) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        for (var route : routes) {
            addRoute(server, route);
        }
        server.start();
    }

    private void addRoute(HttpServer server, Route route) {
        var path = route.path();
        var handler = route.handler();
        server.createContext(path, exchange -> {
            if (!exchange.getRequestURI().getPath().equals(path)) {
                notFound(exchange);
            } else if (exchange.getRequestMethod().equalsIgnoreCase("GET")) {
                handleGet(exchange, handler);
            } else if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                handlePost(exchange, handler);
            }
        });
    }

    private void handleGet(HttpExchange exchange, Object handler) throws IOException {
        if (handler instanceof GetHandler getHandler) {
            var response = getHandler.doGet();
            if (response instanceof String str) {
                sendText(exchange, 200, str);
            } else {
                sendJson(exchange, 200, response);
            }
        } else {
            badRequest(exchange);
        }
    }

    private void handlePost(HttpExchange exchange, Object handler) throws IOException {
        if (handler instanceof PostHandler postHandler) {
            var request = mapper.readValue(exchange.getRequestBody().readAllBytes(), Params.class);
            if (request instanceof Params params) {
                postHandler.doPost(params);
            }
            sendEmpty(exchange, 200);
        } else {
            badRequest(exchange);
        }
    }

    private void notFound(HttpExchange exchange) throws IOException {
        sendEmpty(exchange, 404);
    }

    private void badRequest(HttpExchange exchange) throws IOException {
        sendEmpty(exchange, 400);
    }

    private void sendText(HttpExchange exchange, int status, String text) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "text/plain; charset=utf-8");
        send(exchange, status, text.getBytes(StandardCharsets.UTF_8));
    }

    private void sendJson(HttpExchange exchange, int status, Object value) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");
        send(exchange, status, mapper.writeValueAsBytes(value));
    }

    private void sendEmpty(HttpExchange exchange, int status) throws IOException {
        exchange.sendResponseHeaders(status, -1);
        exchange.close();
    }

    private void send(HttpExchange exchange, int status, byte[] body) throws IOException {
        exchange.sendResponseHeaders(status, body.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(body);
        }
        exchange.close();
    }


}

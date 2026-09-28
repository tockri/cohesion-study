package com.example.study;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        TodoRepository todoRepository = new TodoRepository();

        var routes = List.of(
                new Route("/", new HelpHandler()),
                new Route("/todos", new TodosHandler(todoRepository))
        );

        new ApiServer().start(8080, routes);

        System.out.println("Server started: http://localhost:8080/");
    }
}

package com.example.study;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TodoRepository {

    private final List<Todo> todos = new ArrayList<>() {
        {
            add(new Todo(1, "犬の散歩"));
            add(new Todo(2, "夕飯の買い物"));
        }
    };

    private final AtomicInteger nextId = new AtomicInteger(3);

    public List<Todo> findAll() {
        return todos;
    }

    public Todo add(String title) {
        Todo todo = new Todo(nextId.getAndIncrement(), title);
        todos.add(todo);
        return todo;
    }
}

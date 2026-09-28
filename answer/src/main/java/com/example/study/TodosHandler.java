package com.example.study;

public class TodosHandler implements GetHandler, PostHandler {
    private final TodoRepository todoRepository;

    public TodosHandler(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public Object doGet() {
        return todoRepository.findAll();
    }

    @Override
    public void doPost(Params request) {
        var title = request.get("title");
        todoRepository.add(title);
    }
}

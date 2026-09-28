package com.example.study;

public class HelpHandler implements GetHandler {

    @Override
    public Object doGet() {
        return "GET  /todos  Todoの一覧を取得する\nPOST /todos  Todoを追加する\n";
    }
}

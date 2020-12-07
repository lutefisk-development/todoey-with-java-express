package com.company.entities;

public class Todo {

    private int id;
    private String todo;
    private boolean completed;

    public Todo() {}

    public Todo(String todo, boolean completed) {
        this.todo = todo;
        this.completed = completed;
    }

    public Todo(boolean completed) {
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", todo='" + todo + '\'' +
                ", completed=" + completed +
                '}';
    }
}

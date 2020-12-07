package com.company;

import com.company.entities.Todo;
import express.Express;
import express.middleware.Middleware;
import server.Database;
import server.Port;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {

        Express app = new Express();
        Database db = new Database();

        // Gets the port to listen to
        Port port = new Port();

        // Endpoints:
        // get all todos
        app.get("/api/todos", (req,res) -> {
            res.json(db.getTodos());
        });

        // get todo by id
        app.get("/api/todos/:id", (req, res) -> {
            try {
                res.json(db.getTodoById(Integer.parseInt(req.getParam("id"))));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        });

        // create todo
        app.post("/api/todos", (req, res) -> {
            Todo newTodo = (Todo) req.getBody(Todo.class);
            int id = db.createTodo(newTodo);

            if(id != 0) {
                newTodo.setId(id);

                Todo todo = db.getTodoById(id);

                if(todo == null) {
                    System.out.println("There is no record of this todo");
                } else {
                    System.out.println(todo.toString());

                    res.send("Successfully created a new todo");
                }

            } else {
                System.out.println("Something went wrong");
            }
        });

        // update todo
        app.put("/api/todos/:id", (req, res) -> {
            try {

                // getting value from user
                Todo todo = (Todo) req.getBody(Todo.class);

                // creating a todo to be updated
                Todo todoToBeUpdated = db.getTodoById(Integer.parseInt(req.getParam("id")));
                todoToBeUpdated.setCompleted(todo.isCompleted());

                db.updateTodo(todoToBeUpdated);

                System.out.println(todo.isCompleted());
                System.out.println(todoToBeUpdated.toString());

                res.send("Updated todo with id: " + todoToBeUpdated.getId());

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        });

        // delete todo
        app.delete("/api/todos/:id", (req, res) -> {
            try {
                int id = Integer.parseInt(req.getParam("id"));
                Todo todo = db.getTodoById(id);

                db.deleteTodo(todo);

                res.send("deleted todo with id: " + todo.getId());

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        });

        // Middleware to get correct path
        try {
            app.use(Middleware.statics(Paths.get("src/client").toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        app.listen(port.getPort());
        System.out.println("Server started on port: " + port.getPort());
    }
}

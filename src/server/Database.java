package server;

import com.company.entities.Todo;
import com.fasterxml.jackson.core.JsonProcessingException;
import express.utils.Utils;

import java.sql.*;
import java.util.List;

public class Database {

    private Connection con;

    public Database() {
        try {
            con = DriverManager.getConnection("jdbc:sqlite:todoey.db");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Todo> getTodos() {
        List<Todo> todos = null;

        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM todos");
            ResultSet res = stmt.executeQuery();

            Todo[] todosFromRs = (Todo[]) Utils.readResultSetToObject(res, Todo[].class);

            todos = List.of(todosFromRs);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return todos;
    }

    public Todo getTodoById(int id) {
        Todo todo = null;

        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM todos WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet res = stmt.executeQuery();

            Todo[] todosFromRs = (Todo[]) Utils.readResultSetToObject(res, Todo[].class);

            todo = todosFromRs[0];

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return todo;
    }

    public int createTodo(Todo todo) {
        int newUid = 0;

        try {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO todos (todo, completed) VALUES (?, ?)");
            stmt.setString(1, todo.getTodo());
            stmt.setBoolean(2, todo.isCompleted());

            stmt.executeUpdate();

            ResultSet res = stmt.getGeneratedKeys();

            while(res.next()) {
                newUid = res.getInt(1);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return newUid;
    }

    public void updateTodo(Todo todo) {
        try {
            PreparedStatement stmt = con.prepareStatement("UPDATE todos SET todo = ?, completed = ? WHERE id = ?");
            stmt.setString(1, todo.getTodo());
            stmt.setBoolean(2, todo.isCompleted());
            stmt.setInt(3, todo.getId());

            stmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteTodo(Todo todo) {
        try {
            PreparedStatement stmt = con.prepareStatement("DELETE FROM todos WHERE id = ?");
            stmt.setInt(1, todo.getId());

            stmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

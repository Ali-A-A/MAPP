package com.example.mapp.models;

import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TodoList {
    List<Todo> todos;

    public TodoList() {
        todos = new ArrayList<>();
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
    }

    public void addTodo(Todo todo) {
        todos.add(todo);
    }

    public void removeTodo(String t) {
        for(Iterator<Todo> iterator = todos.iterator(); iterator.hasNext(); ) {
            if(iterator.next().title.equals(t))
                iterator.remove();
        }
    }

    public void setComplete(String t , boolean state) {
        for(Todo todo : todos) {
            if(todo.title.equals(t)) {
                todo.setCompleted(state);
            }
        }
    }

    public Todo getTodoByTitle(String title) {
        for(Todo todo : todos) {
            if(todo.title.equals(title)) {
                return todo;
            }
        }
        return null;
    }
}

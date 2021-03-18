package com.example.mapp.models;

public class Todo {
    String title;
    String content;
    boolean isCompleted;

    public Todo(String title, String content) {
        this.title = title;
        this.content = content;
        this.isCompleted = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }


}

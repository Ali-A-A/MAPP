package com.example.mapp.models;

public class Quot {
    String _id;
    String[] tags;
    String content;
    String author;
    int length;

    public Quot(String _id, String[] tags, String content, String author, int length) {
        this._id = _id;
        this.tags = tags;
        this.content = content;
        this.author = author;
        this.length = length;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}

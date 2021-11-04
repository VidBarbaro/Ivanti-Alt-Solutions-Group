package com.ivantimarket.ivanti.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "packages")
public class Package {

    @Id
    private int id;
    private String title;
    private User creator;

    public Package(int id, String title, User creator) {
        this.id = id;
        this.title = title;
        this.creator = creator;
    }
    public Package(String title, User creator) {
        this.title = title;
        this.creator = creator;
    }
    public Package() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}

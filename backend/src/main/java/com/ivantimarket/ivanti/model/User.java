package com.ivantimarket.ivanti.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Document(collection = "users")
@Getter
@Setter
public class User {

    @Id
    private long id;
    private String name;
    private String username;
    private String password;
    private String email;

    @DBRef
    private Set<Role> roles = new HashSet<>();

    @DBRef
    private Set<Integer> downloaded_packages_id = new HashSet<>();

    @DBRef
    private Set<Integer> uploaded_packages_id = new HashSet<>();

    public User(long id, String name, String username, String password, String email) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
    }
    public User(String name, String username, String password, String email) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
    }
    public User() {

    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this==o)
            return true;
        if (!(o instanceof User))
            return false;
        User u = (User)o;
        return Objects.equals(this.id,u.id);
    }
}
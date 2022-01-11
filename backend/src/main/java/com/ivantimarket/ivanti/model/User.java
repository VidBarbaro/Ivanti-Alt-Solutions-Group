package com.ivantimarket.ivanti.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
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

    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";

    @Id
    private long id;
    private String name;
    private String username;
    private String password;
    private String email;
    private boolean firstTime;

    @DBRef
    private Set<Role> roles = new HashSet<>();

    private Set<Long> favourite_packages_id = new HashSet<>();
    private Set<Long> uploaded_packages_id = new HashSet<>();

    public User(long id, String name, String username, String password, String email) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstTime = true;
    }
    public void addRole(Role role) {
        roles.add(role);
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

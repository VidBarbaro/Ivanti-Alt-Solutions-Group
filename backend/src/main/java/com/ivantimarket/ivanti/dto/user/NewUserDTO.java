package com.ivantimarket.ivanti.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class NewUserDTO {

    private long id;
    private String name;
    private String username;
    private String password;
    private String email;
    private String roleName;

    public NewUserDTO(long id, String name, String username, String password, String email, String roleName) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.roleName = roleName;
    }

    private Set<Long> downloaded_packages_id = new HashSet<>();
    private Set<Long> uploaded_packages_id = new HashSet<>();
}

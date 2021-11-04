package com.ivantimarket.ivanti.dto;

import com.ivantimarket.ivanti.model.Role;
import lombok.Data;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserDTO {
    private long id;
    private String name;
    private String username;
    private String email;
    private Set<Role> roles = new HashSet<>();
}

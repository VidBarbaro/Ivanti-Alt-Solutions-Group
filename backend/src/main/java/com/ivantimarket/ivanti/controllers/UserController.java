package com.ivantimarket.ivanti.controllers;

import com.ivantimarket.ivanti.dto.user.NewUserDTO;
import com.ivantimarket.ivanti.dto.user.UserDTO;
import com.ivantimarket.ivanti.model.User;
import com.ivantimarket.ivanti.service.SequenceGeneratorService;
import com.ivantimarket.ivanti.service.UserService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api")
public class UserController {

    private final UserService userService;

    private final SequenceGeneratorService sequenceGeneratorService;


    public UserController(UserService userService, SequenceGeneratorService sequenceGeneratorService) {
        this.userService = userService;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

        @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok().body(userService.getUserDTOs());
    }
    //personally dont think that this is the way because we do not check whether the user is the same as the one who
    //is updating this user
    @PostMapping("/user/update/password")
    public ResponseEntity<User> updatePasswordUser(HttpServletRequest request, HttpServletResponse response,
                                           @RequestParam("id") long id,
                                           @RequestParam("oldpassword") String oldpassword,
                                           @RequestParam("password") String password) throws IOException {
        User user = userService.getUserByToken(request, response);
        if (userService.getUser(id).getId() == user.getId()) {
            User u = userService.updatePasswordUser(id,oldpassword, password);
            if (u != null) {
                return ResponseEntity.ok(u);
            }
        }
        return ResponseEntity.badRequest().body(null);

    }

    @PostMapping("/user/update")
    public ResponseEntity<User> updateUser(HttpServletRequest request, HttpServletResponse response,
                                           @RequestParam("id") long id,
                                           @RequestParam("name") String name,
                                           @RequestParam("email") String email) throws IOException {
        User user = userService.getUserByToken(request, response);
        if (userService.getUser(id).getId() == user.getId()) {
            User u = userService.updateUser(name, email, id);
            if (u != null) {
                return ResponseEntity.ok(u);
            }
        }
        return ResponseEntity.badRequest().body(null);

    }
    @PostMapping("/user/{userId}/increment-first-time")
    public ResponseEntity<User> incrementFirstTime(@PathVariable("userId")long userId) {
        return ResponseEntity.ok().body(userService.incrementFirstTime(userId));
    }
    @PostMapping("/register")
    public ResponseEntity<UserDTO> saveUser(@RequestBody NewUserDTO userDTO) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        userDTO.setId(sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME));
        return ResponseEntity.created(uri).body(userService.saveUser(userDTO));
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        userService.refreshToken(request, response);
    }

    @PostMapping("/user/add-role")
    public ResponseEntity<User> addRoleToUser(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("userId")long userId, @RequestParam("roleName")String roleName) {
        User user = userService.getUser(userId);
        userService.addRoleToUser(user, roleName);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/user/changeFirstTimeVar")
    public ResponseEntity<User> changeFirstTimeVar(@RequestBody long userId) {
        User user = userService.getUser(userId);
        userService.changeFirstTimeVar(user);
        return ResponseEntity.ok(user);
    }
}

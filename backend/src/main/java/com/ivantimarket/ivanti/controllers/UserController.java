package com.ivantimarket.ivanti.controllers;

import com.ivantimarket.ivanti.dto.NewUserDTO;
import com.ivantimarket.ivanti.dto.UserDTO;
import com.ivantimarket.ivanti.model.User;
import com.ivantimarket.ivanti.repo.UserRepository;
import com.ivantimarket.ivanti.service.SequenceGeneratorService;
import com.ivantimarket.ivanti.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value="/api")
public class UserController {

    private final UserService userService;

    private final SequenceGeneratorService sequenceGeneratorService;


    public UserController(UserService userService, SequenceGeneratorService sequenceGeneratorService) {
        this.userService = userService;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

//    @GetMapping("/users")
//    public ResponseEntity<List<UserDTO>> getUsers() {
//        return ResponseEntity.ok().body(userService.getUserDTOs());
//    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> saveUser(@RequestBody NewUserDTO userDTO) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        userDTO.setId(sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME));
        return ResponseEntity.created(uri).body(userService.saveUser(userDTO));
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        userService.refreshToken(request,response);
    }
}

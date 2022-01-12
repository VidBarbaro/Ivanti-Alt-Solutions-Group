package com.ivantimarket.ivanti.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ivantimarket.ivanti.controllers.UserController;
import com.ivantimarket.ivanti.model.User;
import com.ivantimarket.ivanti.repo.UserRepository;
import com.ivantimarket.ivanti.service.SequenceGeneratorService;
import com.ivantimarket.ivanti.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@AutoConfigureMockMvc
@SpringBootTest(classes = {UserController.class}, properties = {"security.basic.enabled=false","spring.main.lazy-initialization=true"})
@ActiveProfiles(value = "test")
@Import(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;


    @MockBean
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    SequenceGeneratorService sequenceGeneratorService;

    //arrange
    User user1 = new User(1L,"Matei","username","test1234","matei@mail.com");
    User user2 = new User(2L,"David","username1","test1234","david@mail.com");
    User user3 = new User(3L,"John","username2","test1234","john@mail.com");

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void createUserTest() throws Exception {
        //arrange
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(user1);
        //act
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .with(csrf()))
                //assert
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void getUsers() throws Exception {
        //arrange
        List<User> users = new ArrayList<>(Arrays.asList(user1,user2,user3));
        Mockito.when(userRepository.findAll()).thenReturn(users);
        //act
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/users")
                .contentType(MediaType.APPLICATION_JSON))
                //assert
                .andExpect(status().isOk());
    }
}

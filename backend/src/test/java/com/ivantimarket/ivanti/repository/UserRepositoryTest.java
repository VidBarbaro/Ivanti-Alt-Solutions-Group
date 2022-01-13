package com.ivantimarket.ivanti.repository;

import com.ivantimarket.ivanti.dto._mapper.UserMapperImpl;
import com.ivantimarket.ivanti.dto.user.NewUserDTO;
import com.ivantimarket.ivanti.model.User;
import com.ivantimarket.ivanti.repo.RoleRepository;
import com.ivantimarket.ivanti.repo.UserRepository;
import com.ivantimarket.ivanti.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.verify;

public class UserRepositoryTest {
    @Mock
    private UserRepository userRepo;
    @Mock
    private RoleRepository roleRepo;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserMapperImpl userMapper;

    private AutoCloseable autoCloseable;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepo,roleRepo,passwordEncoder,userMapper);
    }
    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }
//    not implemented get all users
    @Test
    void getUserList() {
        userService.getUserDTOs();
        verify(userRepo).findAll();

    }
    @Test
    void getUserByUsername() {
        userService.getUserDTO("pete");
        verify(userRepo).findByUsername("pete");
    }

    // the when setting the password the user is being null
    @Test
   // @Disabled
    void SaveNewUser() {
        NewUserDTO newUserDTO = new NewUserDTO(1,"John Doe","johnny","john123456", "johnny@gmail.com","ROLE_CUSTOMER");
        userService.saveUser(newUserDTO);
        User user = userMapper.toUser(newUserDTO);
        verify(userRepo).save(user);
    }

    @Test
    void getUserById() {
        userService.getUser(1);
        verify(userRepo).findById(1);
    }
}

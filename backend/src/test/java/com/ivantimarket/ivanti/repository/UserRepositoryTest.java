package com.ivantimarket.ivanti.repository;

import com.ivantimarket.ivanti.dto.user.NewUserDTO;
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

    private AutoCloseable autoCloseable;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
//        userService.saveUser(new NewUserDTO(1,"Pete","pete","123456789",
//                "pete@gmail.com","ROLE_CUSTOMER"));
//        this.userService = new UserService
    }
    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }
//    not implemented get all users
    @Test
    @Disabled
    void getUserList() {
        userService.getUserDTOs();
        verify(userRepo).findAll();

    }

    @Test
    @Disabled
    void getUserByUsername() {
        userService.loadUserByUsername("pete");
        verify(userRepo).findByUsername("pete");
    }
    @Test
    void getUserById() {
        userService.getUser(1);
        verify(userRepo).findById(1);
    }
}

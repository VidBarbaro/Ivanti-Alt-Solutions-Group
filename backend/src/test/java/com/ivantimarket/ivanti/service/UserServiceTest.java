package com.ivantimarket.ivanti.service;

import com.ivantimarket.ivanti.dto._mapper.UserMapper;
import com.ivantimarket.ivanti.dto._mapper.UserMapperImpl;
import com.ivantimarket.ivanti.dto.user.NewUserDTO;
import com.ivantimarket.ivanti.dto.user.UserDTO;
import com.ivantimarket.ivanti.model.User;
import com.ivantimarket.ivanti.repo.RoleRepository;
import com.ivantimarket.ivanti.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class UserServiceTest {
    @MockBean
    private UserRepository userRepo;
    @MockBean
    private RoleRepository roleRepo;
    @MockBean
    private BCryptPasswordEncoder passwordEncoder;
    @MockBean
    private UserMapperImpl userMapper;
    private UserService userService;

    @BeforeEach
    void setUp() {
//        passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserService(userRepo,roleRepo, passwordEncoder, userMapper);
        userService.saveUser(new NewUserDTO(1,"Pete","pete","123456789",
                "pete@gmail.com","ROLE_CUSTOMER"));
    }
    @Test
    @Disabled
    void getUserById(){
        User user = null;

        Mockito.when(userRepo.findById(1)).thenReturn(user);

        User result = userService.getUser(1);

        assertEquals(result,user);
    }
}
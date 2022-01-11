package com.ivantimarket.ivanti.model;

import com.ivantimarket.ivanti.dto.user.UserDTO;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    User user;
    Role role;
    Role role1;
    Package aPackage;
    Version version;
    UserDTO userDTO;
    @BeforeEach
    void setUp() {
        role = new Role(1,"ROLE_CUSTOMER");
        role1 = new Role(2,"ROLE_CONTENT_CREATOR");
        //user dto - creator
        userDTO = new UserDTO();
        userDTO.setUsername("pete");
        userDTO.setId(1);
        userDTO.setEmail("pete@gmail.com");
        userDTO.setName("Pete Smith");
        //version
        List<Version> versions = new ArrayList<>();
        version = new Version();
        LocalDateTime localDateTime = LocalDate.of(2020, Month.JANUARY, 18).atStartOfDay();
        version.setDateAdded(localDateTime);
        version.setUrl("urlrlrl");
        version.setName("first");
        version.setReadme("readme");
        version.setSize(1234);
        versions.add(version);
        //package
//        aPackage = new Package(1,"Title", userDTO, "The new intro", ,null);
        aPackage = new Package();
        aPackage.setId(1);
        aPackage.setCreator(userDTO);
        aPackage.setTitle("title");
        aPackage.setIntro("The new Intro");
        aPackage.setSystemRequirements(new SystemRequirements("Intem core i5","4gb","RX 560"));
        aPackage.setVersions(versions);
        //user
        user = new User(1,"John Doe","johnny","john123456", "johnny@gmail.com");
//        user.setName("John Doe");
//        user.setUsername("johnny");
//        user.setEmail("johnny@gmail.com");
//        user.setPassword("john123456");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
    }

    @Test
    void ChangeNameUser() {
        // arrange

        // act
        user.setName("Johnny");

        // assert
        Assertions.assertEquals("Johnny", user.getName());
    }

    @Test
    void CheckPasswordUser() {
        // arrange

        // act

        // assert
        Assertions.assertEquals("john123456", user.getPassword());
    }

    @Test
    void ChangeEmailToEmptyString() {
        // arrange

        // act
        user.setEmail("");

        // assert
        Assertions.assertEquals("", user.getEmail());
    }
    @Test
    void AddNewRole() {
        // arrange
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        roles.add(role1);

        // act
        user.setRoles(roles);
        // assert
        Assertions.assertEquals(2, user.getRoles().size());
    }

//    @Test
//    @Disabled
//    void CustomersetDownloaded_packages_id() {
//        //arrange
//        Set<Long>packages = new HashSet<>();
//        //act
//        packages.add(aPackage.getId());
//        user.setDownloaded_packages_id(packages);
//        //assert
//        Assertions.assertEquals(1,user.getDownloaded_packages_id().size());
//    }

    @Test
    void NoContentCreatorSetUploaded_packages_id() {
        //arrange
        Set<Long>packages = new HashSet<>();
        //act
        packages.add(aPackage.getId());
        user.setUploaded_packages_id(packages);
        //assert
        Assertions.assertEquals(1,user.getUploaded_packages_id().size());
    }
}
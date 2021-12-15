package com.ivantimarket.ivanti.model;

import com.ivantimarket.ivanti.dto.user.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PackageTest {
    Package aPackage;
    Version version;
    UserDTO userDTO;
    List<Version> versions;
    @BeforeEach
    void setUp() {
        //user dto - creator
        userDTO = new UserDTO();
        userDTO.setUsername("pete");
        userDTO.setId(1);
        userDTO.setEmail("pete@gmail.com");
        userDTO.setName("Pete Smith");
        //version
        versions = new ArrayList<>();
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
    }

    @Test
    void ChangePackageName() {
        //arrange

        //act
        aPackage.setTitle("The Neeew Title");
        //assert
        Assertions.assertEquals("The Neeew Title",aPackage.getTitle());
    }
    @Test
    void AddOneMoreVersion() {
        //arrange
        version = new Version();
        LocalDateTime localDateTime = LocalDate.of(2020, Month.FEBRUARY, 18).atStartOfDay();
        version.setDateAdded(localDateTime);
        version.setUrl("D:/url/B");
        version.setName("Second");
        version.setReadme("readme twice");
        version.setSize(4321);

        //act
        versions.add(version);
        //assert
        Assertions.assertEquals(2,aPackage.getVersions().size());
    }
}
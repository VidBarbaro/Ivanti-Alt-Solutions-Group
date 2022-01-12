package com.ivantimarket.ivanti.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivantimarket.ivanti.controllers.PackageController;
import com.ivantimarket.ivanti.dto.user.UserDTO;
import com.ivantimarket.ivanti.repo.PackageRepository;
import com.ivantimarket.ivanti.service.FileService;
import com.ivantimarket.ivanti.service.PackageService;
import com.ivantimarket.ivanti.service.SequenceGeneratorService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import com.ivantimarket.ivanti.model.Package;
import org.mockito.Mock;
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
@SpringBootTest(classes = {PackageController.class}, properties = {"security.basic.enabled=false","spring.main.lazy-initialization=true"})
@ActiveProfiles(value = "test")
@Import(PackageController.class)
public class PackageControllerTest {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;


    @MockBean
    PackageService packageService;

    @MockBean
    PackageRepository packageRepository;

    @MockBean
    SequenceGeneratorService sequenceGeneratorService;

    @MockBean
    FileService fileService;

    Package package1 = new Package();
    Package package2 = new Package();
    Package package3 = new Package();

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void getAllPackagesTest() throws Exception {
        //arrange
        List<Package> packages = new ArrayList<>(Arrays.asList(package1,package2,package3));
        Mockito.when(packageRepository.findAll()).thenReturn(packages);
        //act
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/packages")
                .contentType(MediaType.APPLICATION_JSON))
                //assert
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void getPackageByIdWhenExists() throws Exception {
        //arrange
        package1.setId(1L);
        Mockito.when(packageService.getPackage(package1.getId())).thenReturn(package1);
        //act
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/packages/get/{packageId}",package1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                //assert
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void updatePackageWhenExists() throws Exception {
        //arrange
        package1.setId(1L);
        Mockito.when(packageService.updatePackage(1L,"Title","Desc","Intro","Type","Ram","Card")).thenReturn(package1);
        //act
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/packages/update-package")
                        .with(csrf())
                .param("id","1")
                        .param("title","Title")
                        .param("description","Desc")
                        .param("intro","Intro")
                        .param("processorType","Type")
                        .param("ram","Ram")
                        .param("graphicsCard","Card")
                .contentType(MediaType.APPLICATION_JSON))
                //assert
                .andExpect(status().isOk());
    }
}

package com.ivantimarket.ivanti.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ivantimarket.ivanti.controllers.DownloadController;
import com.ivantimarket.ivanti.model.Download;
import com.ivantimarket.ivanti.repo.DownloadRepository;
import com.ivantimarket.ivanti.service.DownloadService;
import com.ivantimarket.ivanti.service.SequenceGeneratorService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.web.JsonPath;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@AutoConfigureMockMvc
@SpringBootTest(classes = {DownloadController.class}, properties = {"security.basic.enabled=false","spring.main.lazy-initialization=true"})
@ActiveProfiles(value = "test")
@Import(DownloadController.class)
public class DownloadControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    DownloadService downloadService;

    @MockBean
    DownloadRepository downloadRepository;

    @MockBean
    SequenceGeneratorService sequenceGeneratorService;

    //arrange
    Download download1 = new Download(1L,1,1,"Version1");
    Download download2 = new Download(2L,2,1,"Version2");
    Download download3 = new Download(3L,3,1,"Version3");


    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void createDownloadTest() throws Exception {
        //arrange
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(download1);
        //act
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/download/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .with(csrf()))
                //assert
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void getDownloadByIdWhenExists() throws Exception {
        //arrange
        Mockito.when(downloadRepository.findById(1)).thenReturn(download1);
        //act
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/download/{id}",1)
                .contentType(MediaType.APPLICATION_JSON))
                //assert
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void deleteDownloadByIdWhenExists() throws Exception {
        //arrange
        Mockito.when(downloadService.deleteDownload(1)).thenReturn(true);
        //act
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/download/delete/{id}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
                //assert
                .andExpect(status().isOk());
    }

}

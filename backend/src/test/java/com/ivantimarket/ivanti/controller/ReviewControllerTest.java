package com.ivantimarket.ivanti.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ivantimarket.ivanti.controllers.ReviewController;
import com.ivantimarket.ivanti.model.Review;
import com.ivantimarket.ivanti.repo.ReviewRepository;
import com.ivantimarket.ivanti.service.ReviewService;
import com.ivantimarket.ivanti.service.SequenceGeneratorService;
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
import org.springframework.web.servlet.tags.EscapeBodyTag;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@AutoConfigureMockMvc
@SpringBootTest(classes = {ReviewController.class}, properties = {"security.basic.enabled=false","spring.main.lazy-initialization=true"})
@ActiveProfiles(value = "test")
@Import(ReviewController.class)
public class ReviewControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;


    @MockBean
    ReviewService reviewService;

    @MockBean
    ReviewRepository reviewRepository;

    @MockBean
    SequenceGeneratorService sequenceGeneratorService;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void createReviewTest() throws Exception {
        //arrange
        Review review = new Review();
        review.setId(1L);
        review.setRating(4);
        review.setUserId(1L);
        review.setPackageId(1L);
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(review);
        //act
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/review/create")
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .content(requestJson))
                //assert
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void getReviewById() throws Exception {
        //arrange
        Review review = new Review();
        review.setId(1L);
        Mockito.when(reviewService.getReviewById(1L)).thenReturn(review);
        //act
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/review/{id}",1)
                .contentType(MediaType.APPLICATION_JSON))
                //assert
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void deleteReviewById() throws Exception {
        //arrange
        Mockito.when(reviewService.deleteReview(1L)).thenReturn(true);
        //act
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/review/delete/{id}",1)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                //assert
                .andExpect(status().isOk());
    }

}

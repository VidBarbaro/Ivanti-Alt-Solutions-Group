package com.ivantimarket.ivanti;

import com.ivantimarket.ivanti.model.User;
import com.ivantimarket.ivanti.repo.UserRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = IvantiApplication.class)
@SpringBootTest
@AutoConfigureMockMvc
class IvantiApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	private MockMvc mvc;

	@After
	public void tearDown(){

	}



	@Test
	public void testSaveUser() {
		User user = new User(10,"Testing Testing","testUsername","test1234","test@ivanti.nl");
		userRepository.save(user);
		Assert.assertEquals(user, userRepository.save(user));
	}
	@Test
	public void testUserIsSavedCorrectly() {
		List<User> users = userRepository.findAll();
		Assert.assertEquals("test@ivanti.nl",users.get(0).getEmail());
	}


}

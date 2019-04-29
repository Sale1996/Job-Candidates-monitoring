package com.intens.test.task.jobCandidate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JobCandidateControllerTest {

	
	
	private static final String URL_PREFIX = "/api/jobcandidate";

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void testGetAll() throws Exception{
		
		mockMvc.perform(get(URL_PREFIX + "/")).andExpect(status().isOk());
	}
	
	@Test
	public void testGetAllByName() throws Exception{
		
		mockMvc.perform(get(URL_PREFIX + "/name/testId")).andExpect(status().isOk());
	}
	
	
	@Test
	public void testGetAllBySkill() throws Exception{
		
		String json = "[\r\n" + 
				"	3000\r\n" + 
				"]";
		
		mockMvc.perform(post(URL_PREFIX + "/skill/").contentType(contentType).content(json)).andExpect(status().isOk());
	}
	
	
	@Test
	public void testGetOneById() throws Exception{
		
		mockMvc.perform(get(URL_PREFIX + "/1000")).andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.id").value(1000l))
		.andExpect(jsonPath("$.name").value("testId"))
		.andExpect(jsonPath("$.email").value("testEmail@gmail.com"))
		.andExpect(jsonPath("$.telephoneNumber").value("060/000-0000"));
			
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testSave() throws Exception{
		
			
		String json = "{\r\n" + 
				"	\"id\": -1,\r\n" + 
				"	\"name\": \"test\",\r\n" + 
				"	\"email\": \"testSave@gmail.com\",\r\n" + 
				"	\"telephoneNumber\": \"060/111-2222\",\r\n" + 
				"	\"dateOfBirth\": \"1996-01-01\",\r\n" + 
				"	\"skills\": []\r\n" + 
				"}";
		
		
		mockMvc.perform(post(URL_PREFIX + '/').contentType(contentType).content(json)).andExpect(status().isCreated());
			
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteById() throws Exception{
		
		this.mockMvc.perform(delete(URL_PREFIX + "/1000")).andExpect(status().isOk());
	}
	
	
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdate() throws Exception{
		
		String json = "{\r\n" + 
				"	\"id\": -1,\r\n" + 
				"	\"name\": \"test\",\r\n" + 
				"	\"email\": \"testUpdate@gmail.com\",\r\n" + 
				"	\"telephoneNumber\": \"060/111-2222\",\r\n" + 
				"	\"dateOfBirth\": \"1996-01-01\",\r\n" + 
				"	\"skills\": []\r\n" + 
				"}";
		
		
		this.mockMvc.perform(put(URL_PREFIX + "/1000").contentType(contentType).content(json)).andExpect(status().isOk());
	
	}
	
	
	@Test
	@Transactional
	@Rollback(true)
	public void testAddSkill() throws Exception{
		
		this.mockMvc.perform(put(URL_PREFIX + "/addSkill/1000/2000")).andExpect(status().isOk());
	}
	
	
	@Test
	@Transactional
	@Rollback(true)
	public void testRemoveSkill() throws Exception{
		
		this.mockMvc.perform(put(URL_PREFIX + "/removeSkill/1000/1000")).andExpect(status().isOk());
	}
	
	
}

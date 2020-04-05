package com.example.EmergencyRoom;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WebLayerTest {
	
	//Here we test the web layer by using Spring's MockMvc
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private Filter springSecurityFilterChain;
	
	//First we add Spring Security to the tests
	@Before
	public void setup() {
	mockMvc = MockMvcBuilders
	            .webAppContextSetup(context)
	            .addFilters(springSecurityFilterChain)
	            .build();
	}
	
	//We can test that unauthorized attempts are not allowed
	//as well as wrong credentials
	
	@Test
	public void testUnauthorizedAttempt() throws Exception {
		this.mockMvc.perform(get("/")).andDo(print())
			.andExpect(unauthenticated());
	}
	
	@Test
	public void testLoginError() throws Exception {
		this.mockMvc
	    	.perform(formLogin("/login").user("wronguser").password("wrongpassword"))
			.andExpect(unauthenticated());
	}
	
	//Here we can test a successful authentication
	//For security, the details have been removed
	//They can be added again when more tests need to run
	
	@Test
	public void testLoginSuccessful() throws Exception {
		this.mockMvc
	    	.perform(formLogin("/login").user("").password(""))
			.andExpect(authenticated());
	}
	
	//Now we test the handling of requests for authenticated users
	
	@Test
	@WithUserDetails("doctor")
	public void testMainPage()throws Exception {
		this.mockMvc
	    	.perform(get("/"))
	    	.andDo(print()).andExpect(status().isOk())
			.andExpect(content().string(containsString("Hello, doctor")));
	}
	
	@Test
	@WithUserDetails("doctor")
	public void testPatientList()throws Exception {
		this.mockMvc
	    	.perform(get("/patientList"))
	    	.andDo(print()).andExpect(status().isOk())
			.andExpect(content().string(containsString("Patient list")));
	}
	
	//One more test, with role limitations
	
	@Test
	@WithUserDetails("doctor")
	public void testAdminPageDoc()throws Exception {
		this.mockMvc
	    	.perform(get("/userList"))
	    	.andDo(print()).andExpect(status().isForbidden());
	}
	
	@Test
	@WithUserDetails("admin")
	public void testAdminPageAdmin()throws Exception {
		this.mockMvc
	    	.perform(get("/userList"))
	    	.andDo(print()).andExpect(status().isOk())
			.andExpect(content().string(containsString("Username")));
	}
	
	//Last, we test REST api in our controller
	//Here we also try a different way,
	//Using WithMockUser instead of the user details
	@Test
	@WithMockUser(roles={"DOCTOR"})
	public void testListPatientsRest() throws Exception {
		this.mockMvc
			.perform(get("/api/patients"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().string(containsString("content")));
	}

}
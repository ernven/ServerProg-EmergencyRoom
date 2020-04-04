package com.example.EmergencyRoom;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.EmergencyRoom.web.EmerRoomAdminController;
import com.example.EmergencyRoom.web.EmerRoomPatientController;

@RunWith(SpringRunner.class)
@SpringBootTest
class EmergencyRoomApplicationTests {

	//In this class we test the basics of the application,
	//including the controllers, so we add them here

	@Autowired
	private EmerRoomPatientController userController;
	
	@Autowired
	private EmerRoomAdminController adminController;
	
	//We test that they are created correctly
	//This is called smoked testing
	
	@Test
	public void contextLoads() throws Exception {
		assertThat(userController).isNotNull();
		assertThat(adminController).isNotNull();
	}

}

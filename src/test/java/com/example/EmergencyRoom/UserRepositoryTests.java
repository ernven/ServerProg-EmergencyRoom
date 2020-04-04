package com.example.EmergencyRoom;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.EmergencyRoom.domain.User;
import com.example.EmergencyRoom.domain.UserRepository;

//Here we test the repository for our system users

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTests {
	
	//Again, we add the repository for testing
	
	@Autowired
	private UserRepository userRepo;
	
	@Test
	public void findByUserameShouldReturnUser() {
		User user = userRepo.findByUsername("admin");
		assertThat(user.getEmail().equals("admin@health.fi"));
	}
	
	//We test the creation of a new user
	@Test
	public void createNewUser() {
		User testUser = new User("testUser",
				"$2y$12$Epf2ysKHbxeUelDM1ELQHOguodNm0CNHncAuv7GONV04Vpd56qs9a", "testUser@test.com", "DOCTOR");
		userRepo.save(testUser);
		assertThat(userRepo.findById(testUser.getId()).isPresent());
		assertThat(testUser.equals(userRepo.findByUsername(testUser.getUsername())));
	}
	
	//We test the method findByEmail
	@Test
	public void findByEmailTester() {
		User testUser = new User("testUser",
				"$2y$12$Epf2ysKHbxeUelDM1ELQHOguodNm0CNHncAuv7GONV04Vpd56qs9a", "testUser@test.com", "DOCTOR");
		userRepo.save(testUser);
		assertThat(userRepo.findById(testUser.getId()).isPresent());
		assertThat(testUser.equals(userRepo.findByUsername(testUser.getUsername())));
		
		List<User> users = userRepo.findByEmail("testUser@test.com");
		assertThat(!users.isEmpty());
		assertThat(users.get(0).equals(userRepo.findByUsername("testUser")));
	}
	
	//Finally, we test for deletion
	@Test
	public void deleteUser() {
		User testUser = new User("testUser",
				"$2y$12$Epf2ysKHbxeUelDM1ELQHOguodNm0CNHncAuv7GONV04Vpd56qs9a", "testUser@test.com", "DOCTOR");
		userRepo.save(testUser);
		assertThat(userRepo.findById(testUser.getId()).isPresent());
		assertThat(testUser.equals(userRepo.findByUsername(testUser.getUsername())));
		
		userRepo.delete(testUser);
		assertThat(!userRepo.findById(testUser.getId()).isPresent());
	}
}
package com.example.EmergencyRoom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import com.example.EmergencyRoom.domain.Patient;
import com.example.EmergencyRoom.domain.PatientRepository;
import com.example.EmergencyRoom.domain.Status;
import com.example.EmergencyRoom.domain.StatusRepository;
import com.example.EmergencyRoom.domain.User;
import com.example.EmergencyRoom.domain.UserRepository;
*/

@SpringBootApplication
public class EmergencyRoomApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmergencyRoomApplication.class, args);
	}

/*	This method adds some data entries for testing purposes
 
	@Bean
	public CommandLineRunner demo(PatientRepository patRepo, StatusRepository statusRepo, UserRepository userRepo) {
		return (args) -> {
			//We create some users and add them to the repository
			//password is testadmin
			userRepo.save(new User("admin",
					"$2y$12$Epf2ysKHbxeUelDM1ELQHOguodNm0CNHncAuv7GONV04Vpd56qs9a", "admin@health.fi", "ADMIN"));
			//pwd is testdoctor
			userRepo.save(new User("dr.roberts",
					"$2y$12$Wd2IaG2SPpcvHyuCLKrMw.la.YMZQlU5AeKHt1DdkEcYJeKLpLBx.", "roberts@health.fi", "DOCTOR"));

			//We create the available status options
			statusRepo.save(new Status("Cleared"));
			statusRepo.save(new Status("Low severity"));
			statusRepo.save(new Status("Medium severity"));
			statusRepo.save(new Status("Critical"));
			
			//We add some patients for testing
			patRepo.save(new Patient("John", "Paul", "120344-1234", "Kaivokatu 6", "00101", "Helsinki", "j.paul@hitmail.org", 22, statusRepo.findByName("Medium severity").get(0)));
			patRepo.save(new Patient("Mary", "Poppins", "111160-5678", "Tietotie 1", "20140", "Vantaa", "m.poppins@sposti.fi", 24, statusRepo.findByName("Cleared").get(0)));
			patRepo.save(new Patient("David", "King", "010110-0110", "Mannerheimintie 272", "01112", "Helsinki", "d.king@mail.net", 53, statusRepo.findByName("Critical").get(0)));
		};
	}
*/
}
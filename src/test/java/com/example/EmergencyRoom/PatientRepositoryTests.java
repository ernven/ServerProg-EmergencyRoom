package com.example.EmergencyRoom;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.EmergencyRoom.domain.Patient;
import com.example.EmergencyRoom.domain.PatientRepository;
import com.example.EmergencyRoom.domain.Status;
import com.example.EmergencyRoom.domain.StatusRepository;

//These are a set of tests for the Patient repository
//Here is also tested the Status repository, since it's related to the patients.

//To test against our current database (and not with in-memory)
//we use the AutoConfigureTestDatabase annotation

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PatientRepositoryTests {
	
	//We add the repositories
	
	@Autowired
	private PatientRepository patRepo;
	
	@Autowired
	private StatusRepository statusRepo;
	
		// First we do search tests
		@Test
		public void findByLastnameShouldReturnPatient() {
			String lastname = "King";
			List<Patient> patients = patRepo.findByLastName(lastname);
			assertThat(patients).hasSize(1);
			assertThat(patients.get(0).getLastName()).isEqualTo(lastname);
		}
		
		public void findByLastNameIgnoreCaseTester() {
			String lastname = "king";
			List<Patient> patients = patRepo.findByLastNameIgnoreCase(lastname);
			assertThat(patients).hasSize(1);
			assertThat(patients.get(0).getLastName()).isEqualToIgnoringCase(lastname);
		}
		
		@Test
		public void findByStatusTest() {
			String status = "Critical";
			List<Status> statusList = statusRepo.findByName(status);
			assertThat(statusList).hasSize(1);
			assertThat(statusList.get(0).getName()).isEqualTo(status);
		}
		
		// Next we do create tests
		@Test
		public void createNewStatusTest() {
			statusRepo.save(new Status("Dead"));
			List<Status> statusList = statusRepo.findByName("Dead");
			assertThat(!statusList.isEmpty());
			assertThat(statusList.get(0).getName()).isEqualTo("Dead");
		}
		
		@Test
		public void createNewPatientTest() {
			statusRepo.save(new Status("Dead"));
			Patient patient = new Patient("Albert", "Walker", "011100-6666", "Morbid Rd. 6", "00000", "Atlanta",
					"a.walker@walkingdead.com", 66, statusRepo.findByName("Dead").get(0));
			patRepo.save(patient);
			List<Patient> patients = patRepo.findByLastName("Walker");
			assertThat(patients).isNotEmpty();
			assertThat(patients.get(0).equals(patient));
		}
		
		//At last, we test for delete functionality for both Patients and Statuses
		@Test
		public void deletePatientTest() {
			statusRepo.save(new Status("Dead"));
			Patient patient = new Patient("Albert", "Walker", "011100-6666", "Morbid Rd. 6", "00000", "Atlanta",
					"a.walker@walkingdead.com", 66, statusRepo.findByName("Dead").get(0));
			patRepo.save(patient);
			List<Patient> patients = patRepo.findByLastName("Walker");
			assertThat(patients).isNotEmpty();
			assertThat(patients.get(0).equals(patient));
			
			patRepo.delete(patient);
			assertThat(!patRepo.existsById(patient.getId()));
		}
		
		@Test
		public void deletePatientByIdTest() {
			statusRepo.save(new Status("Dead"));
			Patient patient = new Patient("Albert", "Walker", "011100-6666", "Morbid Rd. 6", "00000", "Atlanta",
					"a.walker@walkingdead.com", 66, statusRepo.findByName("Dead").get(0));
			patRepo.save(patient);
			List<Patient> patients = patRepo.findByLastName("Walker");
			assertThat(patients).isNotEmpty();
			assertThat(patients.get(0).equals(patient));
			
			patRepo.deleteById(patient.getId());
			assertThat(!patRepo.findById(patient.getId()).isPresent());
		}
		  
		@Test
		public void deleteStatusTest() {
			statusRepo.save(new Status("Dead"));
			List<Status> statusList = statusRepo.findByName("Dead");
			assertThat(!statusList.isEmpty());
			assertThat(statusList.get(0).getName()).isEqualTo("Dead");
			
			Status toBeDeleted = statusRepo.findByName("Dead").get(0);
			statusRepo.delete(toBeDeleted);
			assertThat(statusRepo.findByName("Dead").isEmpty());
		}

}

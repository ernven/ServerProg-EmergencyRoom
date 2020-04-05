package com.example.EmergencyRoom.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

//Here we have our repository for Patients, with some methods added
//This interface extends the provided CrudRepository, as can be seen below

public interface PatientRepository extends CrudRepository<Patient, Long>  {

	List<Patient> findByLastName(String lastName);
	
	List<Patient> findByLastNameIgnoreCase(String lastName);
	
	List<Patient> findByCity(String city);

}
package com.example.EmergencyRoom.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface PatientRepository extends CrudRepository<Patient, Long>  {

	List<Patient> findByLastName(String lastName);
	
	List<Patient> findByLastNameIgnoreCase(String lastName);
	
	List<Patient> findByCity(String city);

}

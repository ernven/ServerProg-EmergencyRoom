package com.example.EmergencyRoom.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, UUID> {

	User findByUsername(String username);
	
	List<User> findByEmail(String email);
	
}
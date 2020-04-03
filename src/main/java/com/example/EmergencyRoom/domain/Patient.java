package com.example.EmergencyRoom.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
public class Patient {
	
	// Annotations used to create the ID column of the table and set to auto-generating
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String firstName, lastName, hklotunnus, address, postcode, city, email;

	@Positive
	private int roomNo;
	
	@ManyToOne
	@JoinColumn(name = "statusId")
	private Status status;
	
	public Patient() {}
	
	public Patient(String firstName, String lastName, String hklotunnus, String address, String postcode, String city,
			String email, int roomNo, Status status) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.hklotunnus = hklotunnus;
		this.address = address;
		this.city = city;
		this.postcode = postcode;
		this.email = email;
		this.roomNo = roomNo;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getHklotunnus() {
		return hklotunnus;
	}

	public void setHklotunnus(String hklotunnus) {
		this.hklotunnus = hklotunnus;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Patient [firstName=" + firstName + ", lastName=" + lastName + ", address=" + address + ", city=" + city
				+ ", postcode=" + postcode + ", email=" + email + ", status=" + status.getName() + "]";
	}
}
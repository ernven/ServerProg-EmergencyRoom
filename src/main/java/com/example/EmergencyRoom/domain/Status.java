package com.example.EmergencyRoom.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Status {

	// Annotations used to create the ID column of the table and set to auto-generating
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long statusId;
	
	@NotNull
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "status")
	@JsonIgnore
	private List<Patient> patients;

	public Status() {}
	
	public Status(String name) {
		this.name = name;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Patient> getPatients() {
		return patients;
	}

	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}

	@Override
	public String toString() {
		return "Status [name=" + name + ", patients=" + patients + "]";
	}
}
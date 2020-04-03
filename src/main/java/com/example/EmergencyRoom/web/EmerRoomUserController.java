package com.example.EmergencyRoom.web;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.EmergencyRoom.domain.Patient;
import com.example.EmergencyRoom.domain.PatientRepository;
import com.example.EmergencyRoom.domain.StatusRepository;

@Controller
public class EmerRoomUserController {
	
	@Autowired
	private PatientRepository patientRepo;

	@Autowired
	private StatusRepository statusRepo;

	//Methods in this controller are for all users, so we don't use method-level security
	
	//First we have the methods for displaying data in our Thymeleaf templates
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/")
	public String mainPage() {
		return "index";
	}
	
	@GetMapping("/patientDetails/{id}")
	public String patientDetails(@PathVariable("id") Long patientId, Model model) {
		model.addAttribute("patient", patientRepo.findById(patientId));
		model.addAttribute("statusList", statusRepo.findAll());
		return "patientDetails";
	}
	
	@GetMapping("/patientList")
	public String listPatients(Model model) {
		model.addAttribute("patients", patientRepo.findAll());
		return "patientList";
	}
	
	@GetMapping("/add")
	public String addPatient(Model model) {
		model.addAttribute("patient", new Patient());
		model.addAttribute("statusList", statusRepo.findAll());
		return "addPatient";
	}
	
	@GetMapping("/update/{id}")
	public String updatePatient(@PathVariable("id") Long patientId, Model model) {
		model.addAttribute("patient", patientRepo.findById(patientId));
		model.addAttribute("statusList", statusRepo.findAll());
		return "updatePatient";
	}
	
	//Here we save a patient (new or updated).
	//First we validate with the proper annotation
	@PostMapping("/save")
	public String save(@Valid Patient patient, BindingResult bindingResult, @RequestHeader String referer) {
		if (bindingResult.hasErrors()) {
			return "redirect:" + referer;
		}
		patientRepo.save(patient);
		return "redirect:patientList";
	}
	
	@GetMapping("/delete/{id}")
	public String deletePatient(@PathVariable("id") Long patientId, Model model) {
		patientRepo.deleteById(patientId);
		return "redirect:../patientList";
	}

	//Here on we define the RESTful methods
	
	//This method uses REST to return all patients
	@GetMapping("/patients")
	public @ResponseBody List<Patient> patientListRest() {
		return (List<Patient>) patientRepo.findAll();
	}
	
	//This method uses REST to return a patient by their ID
	@GetMapping("/patient/{id}")
	public @ResponseBody Optional<Patient> findPatientRest(@PathVariable("id") Long id) {
		return patientRepo.findById(id);
	}
	
}

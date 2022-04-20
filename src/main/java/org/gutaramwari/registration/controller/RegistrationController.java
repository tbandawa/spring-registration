package org.gutaramwari.registration.controller;

import java.util.List;

import javax.validation.Valid;

import org.gutaramwari.registration.exception.ResourceNotFoundException;
import org.gutaramwari.registration.model.Attendant;
import org.gutaramwari.registration.model.RegistrationStatus;
import org.gutaramwari.registration.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/registration")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "registration", description = "upload, view, and edit attendant")
public class RegistrationController {
	
	@Autowired
	private RegistrationService registrationService;
	
	@PostMapping("import")
	@Operation(summary = "consume register", description = "upload register excel sheet")
	public ResponseEntity<RegistrationStatus> importRegister(
			@RequestParam("date") String date,
			@RequestParam("register") MultipartFile register
	) {
		RegistrationStatus registrationStatus = registrationService.importRegister(register, date);
		return new ResponseEntity<>(registrationStatus, HttpStatus.OK);
	}
	
	@GetMapping("attendants")
	@Operation(summary = "get all attendants", description = "retrieves a list of attendants")
	public ResponseEntity<?> getAttendants() {
        List<Attendant> attendants = registrationService.getAllAttendants();
        return new ResponseEntity<>(attendants, HttpStatus.OK);
	}
	
    @GetMapping("attendants/{id}")
    @Operation(summary = "get a single attendant", description = "get attendant by <b>id</b>")
    public ResponseEntity<?> getAttendant(@PathVariable Long id) {
    	Attendant attendant = registrationService.getAttendant(id)
    			.orElseThrow(() -> new ResourceNotFoundException("Attendant with id: " + id + " not found"));
    	return new ResponseEntity<>(attendant, HttpStatus.OK);
    }
    
    @PostMapping("attendants")
    @Operation(summary = "create/edit attendant", description = "create a new attendant or edit an existing attendant")
    public ResponseEntity<Attendant> createAttendant(@Valid @RequestBody Attendant attendant) {
    	Attendant savedAttendant = registrationService.saveAttendent(attendant);
    	return new ResponseEntity<>(savedAttendant, HttpStatus.CREATED);
    }
	
}
package com.utem.dadProject.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utem.dadProject.Service.PatientService;
import com.utem.dadProject.model.Patient;

@RestController
@RequestMapping("/patient")
public class PatientController {
	
	@Autowired
	private PatientService patientService;
	
	@PostMapping("/add")
	public void addPatient(@RequestBody Patient patient)
	{
		patientService.addPatient(patient);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Patient> searchPatient(@PathVariable int id)
	{
		try {
			Patient patient = patientService.findPatient(id);
			if(patient == null)
			{
				 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}else
			{
				return new ResponseEntity<>(patient,HttpStatus.OK);
			}
		}catch(Exception e)
		{
			 e.printStackTrace();
 	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PatchMapping("/update")
	public ResponseEntity<Patient> updatePatient(@RequestBody Patient patient)
	{
		try {
			patientService.updatePatient(patient);
			return new ResponseEntity<>(patient,HttpStatus.OK);
		}catch(Exception e)
		{
			 e.printStackTrace();
 	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@DeleteMapping("/delete/{patientID}")
	public ResponseEntity<Patient> deletePatient(@PathVariable int patientID)
	{
		try {
			Patient patient = patientService.findPatient(patientID);
			if(patient.equals(null))
			{
				 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			patientService.deletePatient(patientID);
			return new ResponseEntity<>(patient,HttpStatus.OK);
		}catch(Exception e)
		{
			 e.printStackTrace();
 	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	
//Delete patient: DELETE "/patient/delete/{id}"
//	Update patient: PATCH "/patient/update"

	
	
}

package com.utem.dadProject.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utem.dadProject.Service.DoctorService;
import com.utem.dadProject.model.Doctor;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
	
	@Autowired 
	private DoctorService doctorService;
	@GetMapping("/")
	public ResponseEntity<List<Doctor>> findDoctors()
	{
		try
		{
			List<Doctor> doctors = doctorService.findDoctorList();
			
			if(doctors == null)
			{
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return ResponseEntity.ok(doctors);
		}catch(Exception e)
		{
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{doctorID}")
	public ResponseEntity<Doctor> findDoctors(@PathVariable int doctorID)
	{
		try
		{
			 Doctor doctor = doctorService.findDoctor(doctorID);
			
			if(doctor == null)
			{
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return ResponseEntity.ok(doctor);
		}catch(Exception e)
		{
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	

}

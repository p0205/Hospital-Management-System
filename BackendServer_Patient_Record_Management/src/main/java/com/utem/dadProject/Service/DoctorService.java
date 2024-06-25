package com.utem.dadProject.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utem.dadProject.model.Doctor;
import com.utem.dadProject.repo.DoctorRepo;

@Service
public class DoctorService {

	@Autowired
	private DoctorRepo doctorRepo;
	
	public List<Doctor> findDoctorList() {
		List<Doctor> doctors = doctorRepo.findAll();
		return doctors;
	}

	public Doctor findDoctor(int doctorID) {
		return doctorRepo.findById(doctorID).orElse(null);
	}
	
	

}

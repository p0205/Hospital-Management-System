package com.utem.dadProject.Service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utem.dadProject.model.Patient;
import com.utem.dadProject.repo.PatientRepo;

@Service
public class PatientService {
	
	@Autowired
	private PatientRepo patientRepo;
	
	public void addPatient(Patient patient)
	{
		patientRepo.save(patient);
	}
	
	public Patient findPatient(int id)
	{
		Optional<Patient> patient = patientRepo.findById(id);
		return patient.orElse(null);
	}
	
	public void deletePatient(int patientID)
	{
		patientRepo.deleteById(patientID);
	}
	
	public void updatePatient(Patient patient)
	{
		Patient tempPatient = findPatient(patient.getId());
		
		if(tempPatient!=null)
		{
			if(patient.getName()!=null && !Objects.equals(tempPatient.getName(), patient.getName()))
			{
				tempPatient.setName(patient.getName());
				patientRepo.save(tempPatient);
			}
			if(patient.getAddress()!=null &&!Objects.equals(tempPatient.getAddress(), patient.getAddress()))
			{
				tempPatient.setAddress(patient.getAddress());
				patientRepo.save(tempPatient);
			}
			if(patient.getDob()!=null &&!Objects.equals(tempPatient.getDob(), patient.getDob()))
			{
				tempPatient.setDob(patient.getDob());
				patientRepo.save(tempPatient);
			}
			if(patient.getEmail()!=null &&!Objects.equals(tempPatient.getEmail(), patient.getEmail()))
			{
				tempPatient.setEmail(patient.getEmail());
				patientRepo.save(tempPatient);
			}
			if(patient.getPhone()!=null &&!Objects.equals(tempPatient.getPhone(), patient.getPhone()))
			{
				tempPatient.setPhone(patient.getPhone());
				patientRepo.save(tempPatient);
			}
		}
		
		
	}
}


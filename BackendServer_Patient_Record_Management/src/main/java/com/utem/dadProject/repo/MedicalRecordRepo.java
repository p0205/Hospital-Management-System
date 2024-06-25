package com.utem.dadProject.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utem.dadProject.model.MedicalRecord;

@Repository
public interface MedicalRecordRepo extends JpaRepository<MedicalRecord,Integer>{
	
	List<MedicalRecord> findByPatientId(int patientID);
}

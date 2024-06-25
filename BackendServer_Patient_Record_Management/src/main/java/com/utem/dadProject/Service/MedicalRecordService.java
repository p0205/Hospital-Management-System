package com.utem.dadProject.Service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utem.dadProject.model.MedicalRecord;
import com.utem.dadProject.repo.MedicalRecordRepo;

@Service
public class MedicalRecordService {

	@Autowired
	private MedicalRecordRepo recordRepo;
	
	public void addRecord(MedicalRecord record) {
		
		recordRepo.save(record);
		
	}

	public MedicalRecord searchRecord(int id) {
		
		return recordRepo.findById(id).orElse(null);
	}
	
	
	public void updatePatient(MedicalRecord record)
	{
		MedicalRecord tempRecord = searchRecord(record.getId());
		
		if(tempRecord!=null)
		{
			if(record.getDate()!=null && !Objects.equals(tempRecord.getDate(), record.getDate()))
			{
				tempRecord.setDate(record.getDate());
				recordRepo.save(tempRecord);
			}
			if(record.getDiagnosis()!=null &&!Objects.equals(tempRecord.getDiagnosis(), record.getDiagnosis()))
			{
				tempRecord.setDiagnosis(record.getDiagnosis());
				recordRepo.save(tempRecord);
			}
			if(record.getDoctor()!=null &&!Objects.equals(tempRecord.getDoctor(), record.getDoctor()))
			{
				tempRecord.setDoctor(record.getDoctor());
				recordRepo.save(tempRecord);
			}
			if(record.getFollowUpDate()!=null &&!Objects.equals(tempRecord.getFollowUpDate(), record.getFollowUpDate()))
			{
				tempRecord.setFollowUpDate(record.getFollowUpDate());
				recordRepo.save(tempRecord);
			}
		
			if(record.getTreatment()!=null &&!Objects.equals(tempRecord.getTreatment(), record.getTreatment()))
			{
				tempRecord.setTreatment(record.getTreatment());
				recordRepo.save(tempRecord);
			}
		
		}
		
		
	}


	public List<MedicalRecord> searchRecordList(int patientID) {
		return recordRepo.findByPatientId(patientID);
	}

	public void deleteRecord(int recordID) {
		recordRepo.deleteById(recordID);	
	}
}

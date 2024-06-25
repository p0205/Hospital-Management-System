package com.utem.dadProject.Controller;

import java.util.List;
import java.util.stream.Collectors;

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

import com.utem.dadProject.Service.DoctorService;
import com.utem.dadProject.Service.MedicalRecordService;
import com.utem.dadProject.Service.PatientService;
import com.utem.dadProject.model.Doctor;
import com.utem.dadProject.model.MedicalRecord;
import com.utem.dadProject.model.MedicalRecordDTO;
import com.utem.dadProject.model.Patient;

@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {
	
	@Autowired
    private MedicalRecordService medService;

    @Autowired
    private PatientService patientService;
    
    @Autowired
    private DoctorService doctorService;



	
	@PostMapping("/{patientID}/add")
	public ResponseEntity<MedicalRecord> addRecord(@PathVariable(value="patientID") int patientID,@RequestBody MedicalRecordDTO recordDTO)
	{
		try
		{
			Patient patient = patientService.findPatient(patientID);
			Doctor doctor = doctorService.findDoctor(recordDTO.getDoctorID());
			if(patient == null|| doctor ==null) 
			{
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			MedicalRecord record = MedicalRecord.builder()
									.date(recordDTO.getDate())
									.diagnosis(recordDTO.getDiagnosis())
									.treatment(recordDTO.getTreatment())
									.followUpDate(recordDTO.getFollowUpDate())
									.doctor(doctor)
									.patient(patient)
									.build();
	
			medService.addRecord(record);
			return ResponseEntity.ok(record);
			
		}catch(Exception e)
		{
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	

	@GetMapping("/{patientID}")
	public ResponseEntity<List<MedicalRecordDTO>> searchRecordList(@PathVariable int patientID) {
	    try {
	        List<MedicalRecord> records = medService.searchRecordList(patientID);

	        if (records == null || records.isEmpty()) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }

	        List<MedicalRecordDTO> recordDTOs = records.stream().map(record -> {
	            MedicalRecordDTO dto = new MedicalRecordDTO();
	            dto.setId(record.getId());
	            dto.setDate(record.getDate());
	            dto.setDiagnosis(record.getDiagnosis());
	            dto.setDoctorID(record.getDoctor().getId());
	            dto.setFollowUpDate(record.getFollowUpDate());
	            dto.setPatientID(record.getPatient().getId());
	            dto.setTreatment(record.getTreatment());
	            return dto;
	        }).collect(Collectors.toList());

	        return ResponseEntity.ok(recordDTOs);
	    } catch (Exception e) {
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
	@GetMapping("/{patientID}/{recordID}")
	public ResponseEntity<MedicalRecordDTO> searchRecord(@PathVariable int patientID, @PathVariable int recordID) {
	    try {
	        MedicalRecord record = medService.searchRecord(recordID);
	        if (record == null) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        } else {
	            MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
	            medicalRecordDTO.setId(record.getId());
	            medicalRecordDTO.setDate(record.getDate());
	            medicalRecordDTO.setDiagnosis(record.getDiagnosis());
	            medicalRecordDTO.setDoctorID(record.getDoctor().getId());
	            medicalRecordDTO.setFollowUpDate(record.getFollowUpDate());
	            medicalRecordDTO.setPatientID(record.getPatient().getId());
	            medicalRecordDTO.setTreatment(record.getTreatment());
	            return ResponseEntity.ok(medicalRecordDTO);
	        }

	    } catch (Exception e) {
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	@PatchMapping("/{patientID}/{recordID}/update")
	public ResponseEntity<MedicalRecordDTO> updateRecord(@PathVariable int patientID, @PathVariable int recordID, @RequestBody MedicalRecordDTO recordDTO) {
	    try {
	       Patient patient  = patientService.findPatient(recordDTO.getPatientID());
	       Doctor doctor = doctorService.findDoctor(recordDTO.getDoctorID());
	       if(patient == null || doctor == null)
	       {
	    	   return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	       }
	       MedicalRecord medicalRecord =  MedicalRecord.builder()
	    		   .id(recordDTO.getId())
	    		   .diagnosis(recordDTO.getDiagnosis())
	    		   .treatment(recordDTO.getTreatment())
	    		   .date(recordDTO.getDate())
	    		   .followUpDate(recordDTO.getFollowUpDate())
	    		   .doctor(doctor)
	    		   .patient(patient)
	    		   .build();
	       medService.updatePatient(medicalRecord);
	       return ResponseEntity.ok(recordDTO);
	    } catch (Exception e) {
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	

	//Delete a medical record: DELETE "/medicalRecord/{recordID}/delete"
	@DeleteMapping("/{recordID}/delete")
	public ResponseEntity<MedicalRecord> deleteRecord(@PathVariable int recordID) {
	    try {
	    	MedicalRecord record = medService.searchRecord(recordID);
	    	if(record == null)
	    	{
	    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    	}
	    	medService.deleteRecord(recordID);
	    	return ResponseEntity.ok(record);
	    } catch (Exception e) {
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
}

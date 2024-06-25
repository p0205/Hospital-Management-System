package com.utem.dadProject.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordDTO {

		private int id;
	    private LocalDate date;
	    private String diagnosis;
	    private String treatment;
	    private LocalDate followUpDate;
	    private int patientID;
	    private int doctorID;

}

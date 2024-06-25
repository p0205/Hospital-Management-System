package com.utem.dadProject.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.utem.dadProject.model.Patient;


//<Patient, Integer> in your repository interface
//telling Spring Data JPA that your repository deals with entities of type Patient and primary keys of type Integer.
@Repository
public interface PatientRepo extends JpaRepository<Patient,Integer>{

}

package com.utem.dadProject.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utem.dadProject.model.Doctor;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor,Integer>{

}

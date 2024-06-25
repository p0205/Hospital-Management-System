package com.utem.dadProject.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utem.dadProject.model.User;


@Repository
public interface UserRepo extends JpaRepository<User,String>{
	
	public User findByUsername(String username);
}

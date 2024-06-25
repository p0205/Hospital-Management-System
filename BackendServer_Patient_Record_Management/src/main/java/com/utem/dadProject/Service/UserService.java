package com.utem.dadProject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utem.dadProject.model.User;
import com.utem.dadProject.repo.UserRepo;

@Service
public class UserService {
	@Autowired
	private UserRepo userRepo;
	
	
	public boolean authenticate(String username, String password)
	{
		User user =  userRepo.findByUsername(username);
		
		if(user.getUsername().equals(username) && user.getPassword().equals(password))
		{
			return true; 
		}
		return false;
	}
}

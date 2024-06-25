package com.utem.dadProject.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utem.dadProject.Service.UserService;
import com.utem.dadProject.model.LoginRequest;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	

	@Autowired
	private UserService userSerivce;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
		
		if(userSerivce.authenticate(loginRequest.getUsername(), loginRequest.getPassword()))
		{
			
			return ResponseEntity.ok("authenticated");
		}
		else
		{
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
	}
}



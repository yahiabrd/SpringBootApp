package com.crudapp.dev;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("")
@CrossOrigin
public class UserController {

	@Autowired
	private UserService service;
	
	@GetMapping(value = "/users", produces = {"application/json"})
	public ResponseEntity<List<User>> getAllUsers() {
		try {
			List<User> users = service.getAllUsers();
			
			if(users.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}
			
			return ResponseEntity.ok(users);
			
		}catch(RuntimeException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	@PostMapping("/user/login")
	public ResponseEntity<User> login(@RequestBody String email, @RequestBody String password) throws Exception {
		try {
			User user = service.checkUserLogin(email, password);
			return ResponseEntity.ok(user);
		}catch(RuntimeException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}

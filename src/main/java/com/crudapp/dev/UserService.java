package com.crudapp.dev;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repo;
	
	public List<User> getAllUsers() {
		return repo.findAll();
	}
	
	public User getUserByEmail(String email) {
		return this.getAllUsers().stream().filter(u->u.getEmail().equals(email)).findAny().get();
	}
	
	public User checkUserLogin(String email, String password) throws Exception {
		User user = this.getUserByEmail(email);
		try {
			return user;
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}
}

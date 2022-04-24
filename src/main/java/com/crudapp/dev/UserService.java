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
		return repo.findByEmail(email);
	}
	
	private boolean isEmailAlreadyTaken(String email) {
		return getUserByEmail(email) == null;
	}
	
//	private User getUserByEmailVersion2(String email) {
//		return this.getAllUsers().stream().filter(u->u.getEmail().equals(email)).findAny().get();
//	}
	
	public void addUser(User user) {
		repo.save(user);
	}
	
	public void updateUser(User updatedUser) {
		User user = repo.findByEmail(updatedUser.getEmail());
		if (user != null) {
			User newUser = new User(user.getUserId(), updatedUser.getFirstName(), updatedUser.getLastName(), user.getEmail(), user.getPassword());
			repo.saveAndFlush(newUser);
		}
	}
	
	public void deleteUser(String email) {
		User user = getUserByEmail(email);
		repo.deleteById(user.getUserId());;
	}
}

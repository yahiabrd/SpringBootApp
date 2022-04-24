package com.crudapp.dev;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.crudapp.jwt.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("")
public class UserController {

	@Autowired
	private UserService service;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
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
	
	@GetMapping(value = "/user/email/{email}", produces = {"application/json"})
	public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
		try {
			
			User user = service.getUserByEmail(email);
			
			if(user == null) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}
			
			return ResponseEntity.ok(user);
			
		}catch(RuntimeException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	@PostMapping("/user/login")
	public ResponseEntity<?> login(@RequestBody UserDTO userDTO) throws Exception {
		User user = service.getUserByEmail(userDTO.getUsername());
		
		if (user != null) {
			try {
			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));
			}catch(BadCredentialsException e) {
				throw new Exception("Incorrect username or password", e);
			}
			
			final UserDetails userDetails = userDetailsService.loadUserByUsername(userDTO.getUsername());
			final String jwt = jwtTokenUtil.generateToken(userDetails);
			
			System.out.println(jwt);
			return ResponseEntity.ok(new JwtDTO(jwt));
		}else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
	}
	
	@PostMapping("/user/add")
	public ResponseEntity<User> addUser(@RequestBody User user) {
		service.addUser(user);
		return ResponseEntity.ok(user);
	}
	
	@PutMapping("/user/update")
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		service.updateUser(user);
		return ResponseEntity.ok(user);
	}
	
	@DeleteMapping("/user/delete/{email}")
	public ResponseEntity<String> deleteUser(@PathVariable String email) {
		service.deleteUser(email);
		return ResponseEntity.ok(email);
	}
}

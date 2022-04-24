package com.crudapp.jwt;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.crudapp.dev.UserService;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	UserService userService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		try {
			com.crudapp.dev.User user = userService.getUserByEmail(email); //username is the email
			return new User(user.getEmail(), user.getPassword(), new ArrayList<>());
		}catch(UsernameNotFoundException e) {
			throw new UsernameNotFoundException("");
		}
	}

}

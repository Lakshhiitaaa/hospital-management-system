package com.hms.hospital.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.cognizant.entities.entity.User;
import com.hms.hospital.repository.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		// find the user by email
		User user = userRepo.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

		return new org.springframework.security.core.userdetails.User(
				user.getEmail(), // email
				user.getPassword(), // Encrypted password
				List.of(new SimpleGrantedAuthority(user.getRole().name()))); // roles
	}
}

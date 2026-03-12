package com.hms.hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cognizant.entities.entity.Doctor;
import com.cognizant.entities.entity.Patient;
import com.cognizant.entities.entity.User;
import com.hms.hospital.dto.DoctorSignupDTO;
import com.hms.hospital.dto.LoginDTO;
import com.hms.hospital.dto.PatientSignupDTO;
import com.hms.hospital.dto.SignupWrapperDTO;
import com.hms.hospital.repository.DoctorRepo;
import com.hms.hospital.repository.PatientRepo;
import com.hms.hospital.repository.UserRepo;
import com.hms.hospital.security.JwtUtil;
import com.hms.hospital.service.SignupService;

@RestController
@RequestMapping("/api/auth")
public class SignupLoginController {

	@Autowired
	SignupService service;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtil jwtutil;

	@Autowired
	UserRepo userrRepo;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	PatientRepo patientRepo;

	@Autowired
	DoctorRepo doctorRepo;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDTO login) throws AuthenticationException {
		try {
			// Check if the email and password are correct
			Authentication auth = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							login.getEmail(), login.getPassword()));

			// If authentication succeeds, find the user
			User user = userrRepo.findByEmail(login.getEmail())
					.orElseThrow(() -> new AuthenticationException("User not found") {
					});
			// Get user's role
			String role = user.getRole().name(); // e.g., "ROLE_PATIENT", "ROLE_DOCTOR", "ROLE_ADMIN"
			String normalizedRole = role.replace("ROLE_", "").toLowerCase(); // "patient" | "doctor" | "admin"

			Long id = null;

			// Based on the role, get patientId or doctorId
			if (normalizedRole.equals("patient")) {
				Patient patient = patientRepo.findByUser(user)
						.orElseThrow(() -> {
							return new RuntimeException("Patient Not Found");
						});
				id = patient.getPatientId();

			} else if (normalizedRole.equals("doctor")) {
				Doctor doctor = doctorRepo.findByUser(user)
						.orElseThrow(() -> {
							return new RuntimeException("Doctor Not Found");
						});
				id = doctor.getDoctorId();
			} else if (normalizedRole.equals("admin")) {
				id = 9L;
			}

			// Generate a JWT token with the user's ID and role
			String token = jwtutil.generateToken(id, role);

			// Create a response map to return token and role
			java.util.Map<String, String> body = new java.util.HashMap<>();
			body.put("token", token);
			body.put("role", normalizedRole);

			// Return JSON expected by Angular AuthService: { token, role }
			return ResponseEntity.ok(body);
		} catch (AuthenticationException e) {
			return ResponseEntity.status(401).body("Email or Password incorrect");
		}

	}

	@PostMapping("/signup/patient")
	public ResponseEntity<PatientSignupDTO> signupPatient(@RequestBody SignupWrapperDTO signup) {
		PatientSignupDTO patient = service.registerPatient(signup.getSignupRequest(), signup.getPatientDTO());
		return ResponseEntity.ok(patient);
	}

	@PostMapping("/signup/doctor")
	public ResponseEntity<DoctorSignupDTO> signupDocotr(@RequestBody SignupWrapperDTO signup) {
		DoctorSignupDTO doctor = service.registerDoctor(signup.getSignupRequest(), signup.getDoctorDTO());
		return ResponseEntity.ok(doctor);
	}

}

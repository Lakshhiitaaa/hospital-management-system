package com.hms.hospital.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hms.hospital.dto.DoctorSignupDTO;
import com.hms.hospital.dto.PatientSignupDTO;
import com.hms.hospital.dto.SignupReqDTO;
import com.cognizant.entities.entity.Doctor;
import com.cognizant.entities.entity.Patient;
import com.cognizant.entities.entity.Role;
import com.cognizant.entities.entity.User;
import com.hms.hospital.exceptions.EmailAlreadyExistsException;
import com.hms.hospital.repository.DepartmentRepo;
import com.hms.hospital.repository.DoctorRepo;
import com.hms.hospital.repository.PatientRepo;
import com.hms.hospital.repository.UserRepo;
import com.hms.hospital.service.SignupService;

@Service
public class SingupServiceImp implements SignupService {
	@Autowired
	DoctorRepo doctorRepo;

	@Autowired
	PatientRepo patientRepo;

	@Autowired
	DepartmentRepo departmentRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// Method to register a new patient
	@Override
	public PatientSignupDTO registerPatient(SignupReqDTO signupDTO, PatientSignupDTO patientDTO) {

		// Check if email already exists
		if (userRepo.existsByEmail(signupDTO.getEmail())) {
			throw new EmailAlreadyExistsException("Email Exists");
		} else {
			// Create new User object for authentication
			User user = new User();
			user.setEmail(signupDTO.getEmail());
			user.setRole(Role.ROLE_PATIENT);
			user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));

			// Create new Patient object and link it to the user
			Patient patient = new Patient();
			patient.setUser(user);
			patient.setUsername(patientDTO.getName());
			patient.setAge(patientDTO.getAge());
			patient.setGender(patientDTO.getGender());
			patient.setContact(patientDTO.getContact());

			userRepo.save(user);
			patientRepo.save(patient);
			return patientDTO;
		}
	}

	// Method to register a new doctor
	@Override
	public DoctorSignupDTO registerDoctor(SignupReqDTO signupDTO, DoctorSignupDTO doctorDTO) {

		// Check if email already exists
		if (userRepo.existsByEmail(signupDTO.getEmail())) {
			throw new EmailAlreadyExistsException("Email Exists");
		}

		// Create new User object for authentication
		User user = new User();
		user.setEmail(signupDTO.getEmail());
		user.setRole(Role.ROLE_DOCTOR); // Set role to doctor
		user.setPassword(passwordEncoder.encode(signupDTO.getPassword())); // Encrypt password

		// Create new Doctor object and link it to the user
		Doctor doctor = new Doctor();
		doctor.setUser(user);
		doctor.setUsername(doctorDTO.getName());
		doctor.setDepartment(departmentRepo.findByName(doctorDTO.getDepartmentName()));
		doctor.setSpecialty(doctorDTO.getSpecialty());

		userRepo.save(user);
		doctorRepo.save(doctor);
		return doctorDTO;

	}

}

package com.hms.hospital.service;

import com.hms.hospital.dto.DoctorSignupDTO;
import com.hms.hospital.dto.PatientSignupDTO;
import com.hms.hospital.dto.SignupReqDTO;

public interface SignupService {
	PatientSignupDTO registerPatient(SignupReqDTO signupDTO, PatientSignupDTO patientDTO);

	DoctorSignupDTO registerDoctor(SignupReqDTO signupDTO, DoctorSignupDTO doctorDTO);

}

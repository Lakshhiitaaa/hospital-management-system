package com.hms.hospital.dto;

public class SignupWrapperDTO {
	private SignupReqDTO signupRequest;
	private PatientSignupDTO patientDTO;
	private DoctorSignupDTO doctorDTO;

	public SignupReqDTO getSignupRequest() {
		return signupRequest;
	}

	public void setSignupRequest(SignupReqDTO signupRequest) {
		this.signupRequest = signupRequest;
	}

	public PatientSignupDTO getPatientDTO() {
		return patientDTO;
	}

	public void setPatientDTO(PatientSignupDTO patientDTO) {
		this.patientDTO = patientDTO;
	}

	public DoctorSignupDTO getDoctorDTO() {
		return doctorDTO;
	}

	public void setDoctorDTO(DoctorSignupDTO doctorDTO) {
		this.doctorDTO = doctorDTO;
	}

}
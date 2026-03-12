package com.hms.hospital.dto;

public class DoctorProfileResponse {
	private Long doctorId;
	private String username;
	private String specialty;
	private String department;
	private String email;

	public DoctorProfileResponse() {

	}

	public DoctorProfileResponse(Long doctorId, String username, String specialty, String department, String email) {
		super();
		this.doctorId = doctorId;
		this.username = username;
		this.specialty = specialty;
		this.department = department;
		this.email = email;
	}

	public Long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}

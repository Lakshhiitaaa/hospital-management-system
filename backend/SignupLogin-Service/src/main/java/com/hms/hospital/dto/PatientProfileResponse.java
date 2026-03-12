package com.hms.hospital.dto;

public class PatientProfileResponse {
    private Long patientId;
    private String name;
    private Integer age;
    private String gender;
    private String contact;
    private String email;

    public PatientProfileResponse() {
    }

    public PatientProfileResponse(String name, Integer age, String gender, String contact, String email) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.contact = contact;
        this.email = email;
    }

    public PatientProfileResponse(Long patientId, String name, Integer age, String gender, String contact,
            String email) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.contact = contact;
        this.email = email;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

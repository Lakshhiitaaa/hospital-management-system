package com.hms.hospital.controller;

import com.cognizant.entities.entity.Doctor;
import com.cognizant.entities.entity.Patient;
import com.hms.hospital.dto.DoctorProfileResponse;
import com.hms.hospital.dto.PatientProfileResponse;
import com.hms.hospital.repository.DoctorRepo;
import com.hms.hospital.repository.PatientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/logged")
public class UserProfileController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepo doctorRepo;

    // Get profile of the currently logged-in patient
    @GetMapping("/patient/me")
    public ResponseEntity<?> getPatientProfile(Principal principal) {
        // Check if user is authenticated
        if (principal == null || principal.getName() == null) {
            return ResponseEntity.status(401).body("Unauthorized: missing principal");
        }
        Long patientId;
        try {
            // Convert principal name (user ID) to Long
            patientId = Long.valueOf(principal.getName());
        } catch (NumberFormatException ex) {
            return ResponseEntity.status(400).body("Invalid principal id");
        }

        // Find patient by ID
        Optional<Patient> opt = patientRepository.findById(patientId);
        if (opt.isEmpty()) {
            return ResponseEntity.status(404).body("Patient profile not found");
        }
        Patient p = opt.get();

        // Create response DTO with patient details
        PatientProfileResponse resp = new PatientProfileResponse(
                p.getUsername(),
                p.getAge(),
                p.getGender(),
                p.getContact(),
                (p.getUser() != null ? p.getUser().getEmail() : null));
        return ResponseEntity.ok(resp);
    }

    // Get profile of the currently logged-in doctor
    @GetMapping("/doctor/me")
    public ResponseEntity<?> getDoctorProfile(Principal principal) {
        // Check if user is authenticated
        if (principal == null || principal.getName() == null) {
            return ResponseEntity.status(401).body("Unauthorized: missing principal");
        }
        Long doctorId;
        try {
            // Convert principal name (user ID) to Long
            doctorId = Long.valueOf(principal.getName());
        } catch (NumberFormatException ex) {
            return ResponseEntity.status(400).body("Invalid principal id");
        }

        // Find doctor by ID
        Optional<Doctor> opt = doctorRepo.findById(doctorId);
        if (opt.isEmpty()) {
            // Doctor not found
            return ResponseEntity.status(404).body("Doctor profile not found");
        }
        Doctor d = opt.get();

        // Create response DTO with doctor details
        DoctorProfileResponse resp = new DoctorProfileResponse(

                d.getDoctorId(),
                d.getUsername(),
                d.getSpecialty(),
                d.getDepartment().getName(),
                (d.getUser() != null ? d.getUser().getEmail() : null));
        // Return doctor profile data
        return ResponseEntity.ok(resp);
    }

    // Get list of all patients (for example, admin use)
    @GetMapping("/patient/all")
    public ResponseEntity<List<PatientProfileResponse>> getAllPatients() {
        // Fetch all patients from database
        List<Patient> patients = patientRepository.findAll();

        // Map patient entities to DTOs for response
        List<PatientProfileResponse> responses = patients.stream()
                .map(p -> new PatientProfileResponse(
                        p.getPatientId(),
                        p.getUsername(),
                        p.getAge(),
                        p.getGender(),
                        p.getContact(),
                        (p.getUser() != null ? p.getUser().getEmail() : null)))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
}

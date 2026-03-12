package com.hms.hospital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.entities.entity.Patient;
import com.cognizant.entities.entity.User;

@Repository
public interface PatientRepo extends JpaRepository<Patient, Long> {
	Optional<Patient> findByUser(User user);
}

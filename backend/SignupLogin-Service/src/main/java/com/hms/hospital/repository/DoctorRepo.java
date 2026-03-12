package com.hms.hospital.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hms.hospital.dto.DoctorDTO;
import com.cognizant.entities.entity.Doctor;
import com.cognizant.entities.entity.User;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Long> {
	Optional<Doctor> findByUser(User user);

	List<DoctorDTO> findByDepartmentId(Long departmentId);
}

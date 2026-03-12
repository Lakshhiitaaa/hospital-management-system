package com.hms.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.entities.entity.Department;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Long> {
	Department findByName(String deptname);
}

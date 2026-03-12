package com.example.pharmacy.repository;

import com.cognizant.entities.entity.Medicine;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    List<Medicine> findByNameIgnoreCase(String name);
}

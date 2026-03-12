package com.example.pharmacy.controller;

import com.example.pharmacy.dto.MedicineDTO;
import com.example.pharmacy.service.MedicineService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicines")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @GetMapping("/getAll")
    public List<MedicineDTO> getAll() {
        return medicineService.getAllMedicines();
    }

    @GetMapping("/{id}")
    public MedicineDTO getById(@PathVariable Long id) {
        return medicineService.getMedicineDetails(id);
    }

    @PostMapping
    public MedicineDTO create(@Valid @RequestBody MedicineDTO dto) {
        return medicineService.createMedicine(dto);
    }

    @PutMapping("/{id}")
    public MedicineDTO update(@PathVariable Long id,@Valid @RequestBody MedicineDTO dto) {
        return medicineService.updateMedicine(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        medicineService.deleteMedicine(id);
    }
}

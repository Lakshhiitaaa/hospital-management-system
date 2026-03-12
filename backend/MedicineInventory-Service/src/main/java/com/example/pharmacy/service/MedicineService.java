package com.example.pharmacy.service;

import com.example.pharmacy.dto.MedicineDTO;
import java.util.List;

public interface MedicineService {
    List<MedicineDTO> getAllMedicines();
    MedicineDTO getMedicineDetails(Long id);
    MedicineDTO createMedicine(MedicineDTO dto);
    MedicineDTO updateMedicine(Long id, MedicineDTO dto);
    void deleteMedicine(Long id);
}

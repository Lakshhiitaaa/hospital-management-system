package com.example.pharmacy.service;

import com.example.pharmacy.dto.BatchDTO;
import com.example.pharmacy.dto.MedicineDTO;
import com.cognizant.entities.entity.Batch;
import com.cognizant.entities.entity.Medicine;
import com.example.pharmacy.repository.BatchRepository;
import com.example.pharmacy.repository.MedicineRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MedicineServiceImpl implements MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private BatchRepository batchRepository;

    @Override
    public List<MedicineDTO> getAllMedicines() {
        return medicineRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public MedicineDTO getMedicineDetails(Long id) {
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicine not found"));
        return convertToDto(medicine);
    }
    
    @Override
    public MedicineDTO createMedicine(MedicineDTO dto) {
        // Normalize and trim all fields
        String name = dto.getName().trim().toLowerCase();
        String manufacturer = dto.getManufacturer().trim().toLowerCase();
        String description = dto.getDescription().trim().toLowerCase();
        String dosage = dto.getDosage().trim().toLowerCase();
        String category = dto.getCategory().trim().toLowerCase();
        String commonUses = dto.getCommonUses().trim().toLowerCase();

        boolean exists = medicineRepository.findAll().stream().anyMatch(m ->
            m.getName().trim().equalsIgnoreCase(name) &&
            m.getManufacturer().trim().equalsIgnoreCase(manufacturer) &&
            m.getDescription().trim().equalsIgnoreCase(description) &&
            m.getDosage().trim().equalsIgnoreCase(dosage) &&
            m.getCategory().trim().equalsIgnoreCase(category) &&
            m.getCommonUses().trim().equalsIgnoreCase(commonUses)
        );

        if (exists) {
            throw new RuntimeException("Medicine already exists. Please use 'Add Batch' instead.");
        }

        // Save new medicine
        Medicine medicine = new Medicine();
        medicine.setName(dto.getName().trim());
        medicine.setManufacturer(dto.getManufacturer().trim());
        medicine.setDescription(dto.getDescription().trim());
        medicine.setDosage(dto.getDosage().trim());
        medicine.setCategory(dto.getCategory().trim());
        medicine.setCommonUses(dto.getCommonUses().trim());

        medicine = medicineRepository.save(medicine);

        // Save batches if provided
        if (dto.getBatches() != null) {
            for (BatchDTO batchDto : dto.getBatches()) {
                if (batchRepository.existsByBatchNumberAndMedicine(batchDto.getBatchNumber(), medicine)) {
                    throw new RuntimeException("Batch number '" + batchDto.getBatchNumber() + "' already exists for this medicine.");
                }

                Batch batch = new Batch();
                batch.setMedicine(medicine);
                batch.setBatchNumber(batchDto.getBatchNumber().trim());
                batch.setStockLevel(batchDto.getStockLevel());
                batch.setUnitPrice(batchDto.getUnitPrice());
//                batch.setExpiryDate(LocalDate.parse(batchDto.getExpiryDate()));
                batch.setExpiryDate(batchDto.getExpiryDate());  // ✅

                
                medicine.getBatches().add(batch);


                batchRepository.save(batch);
            }
        }

        // 🔁 Reload medicine with batches
        Medicine savedMedicine = medicineRepository.findById(medicine.getMedicineId())
                .orElseThrow(() -> new RuntimeException("Failed to fetch saved medicine"));

        savedMedicine.getBatches().size(); // force load batches

        // ✅ Return DTO from fully loaded object
        return convertToDto(savedMedicine);
    }


    @Override
    public MedicineDTO updateMedicine(Long id, MedicineDTO dto) {
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicine not found"));

        medicine.setName(dto.getName());
        medicine.setDescription(dto.getDescription());
        medicine.setManufacturer(dto.getManufacturer());
        medicine.setCategory(dto.getCategory());
        medicine.setDosage(dto.getDosage());
        medicine.setCommonUses(dto.getCommonUses());

        return convertToDto(medicine);
    }

    @Override
    public void deleteMedicine(Long id) {
        medicineRepository.deleteById(id);
    }

    private MedicineDTO convertToDto(Medicine medicine) {
        MedicineDTO dto = new MedicineDTO();
        dto.setMedicineId(medicine.getMedicineId());
        dto.setName(medicine.getName());
        dto.setDescription(medicine.getDescription());
        dto.setManufacturer(medicine.getManufacturer());
        dto.setCategory(medicine.getCategory());
        dto.setDosage(medicine.getDosage());
        dto.setCommonUses(medicine.getCommonUses());

        if (medicine.getBatches() != null) {
            List<BatchDTO> batchDTOs = new ArrayList<>();
            for (Batch batch : medicine.getBatches()) {
                BatchDTO b = new BatchDTO();
                b.setBatchId(batch.getBatchId());
                b.setBatchNumber(batch.getBatchNumber());
                b.setStockLevel(batch.getStockLevel());
                b.setUnitPrice(batch.getUnitPrice());
                b.setExpiryDate(batch.getExpiryDate());  // ✅ correct type
                batchDTOs.add(b);
            }
            dto.setBatches(batchDTOs);
        }

        return dto;
    }
    
    
}

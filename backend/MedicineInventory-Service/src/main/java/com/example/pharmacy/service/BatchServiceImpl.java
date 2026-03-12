package com.example.pharmacy.service;

import com.example.pharmacy.dto.BatchDTO;
import com.example.pharmacy.dto.DashboardMetricsDTO;
import com.cognizant.entities.entity.Batch;
import com.cognizant.entities.entity.Medicine;
import com.example.pharmacy.repository.BatchRepository;
import com.example.pharmacy.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BatchServiceImpl implements BatchService {

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private BatchRepository batchRepository;
    
    @Autowired
    public BatchServiceImpl(BatchRepository batchRepository, MedicineRepository medicineRepository) {
        this.batchRepository = batchRepository;
        this.medicineRepository = medicineRepository;
    }

    @Override
    public BatchDTO addBatchToMedicine(Long medicineId, BatchDTO dto) {
        Medicine medicine = medicineRepository.findById(medicineId)
                .orElseThrow(() -> new RuntimeException("Medicine not found"));

        // Check if batch number already exists for this medicine
        if (batchRepository.existsByBatchNumberAndMedicine(dto.getBatchNumber(), medicine)) {
            throw new RuntimeException("Batch number already exists for this medicine.");
        }

        // Add batch
        Batch batch = new Batch();
        batch.setMedicine(medicine);
        batch.setBatchNumber(dto.getBatchNumber().trim());
        batch.setStockLevel(dto.getStockLevel());
        batch.setUnitPrice(dto.getUnitPrice());
        batch.setExpiryDate(dto.getExpiryDate());

//        batch.setDescription(dto.getDescription());  // Optional batch-level description

        batch = batchRepository.save(batch);

        dto.setBatchId(batch.getBatchId());
        return dto;
    }
    
    
    @Override
    public void deleteBatch(Long medicineId, Long batchId) {
        if (!medicineRepository.existsById(medicineId)) {
            throw new RuntimeException("Medicine not found with ID: " + medicineId);
        }

        if (!batchRepository.existsByBatchIdAndMedicine_MedicineId(batchId, medicineId)) {
            throw new RuntimeException("Batch not found with ID: " + batchId + " for medicine ID: " + medicineId);
        }

        batchRepository.deleteById(batchId);
    }
    
    @Override
    public DashboardMetricsDTO getDashboardMetrics() {
        long totalMedicines = batchRepository.countDistinctMedicineId();
        long outOfStockCount = batchRepository.countByStockLevelEquals(0);
        long expiringSoonCount = batchRepository.countExpiringSoon(LocalDate.now().plusMonths(1));
        Double totalInventoryValue = batchRepository.calculateTotalInventoryValue();

        return new DashboardMetricsDTO(
                totalMedicines,
                outOfStockCount,
                expiringSoonCount,
                totalInventoryValue != null ? totalInventoryValue : 0.0
        );
    }

}

package com.example.pharmacy.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cognizant.entities.entity.Batch;
import com.cognizant.entities.entity.Medicine;


public interface BatchRepository extends JpaRepository<Batch, Long> {
    boolean existsByBatchNumberAndMedicine(String batchNumber, Medicine medicine);
    boolean existsByBatchIdAndMedicine_MedicineId(Long batchId, Long medicineId);
    
    @Query("SELECT COUNT(DISTINCT b.medicine.id) FROM Batch b")
    long countDistinctMedicineId();

    long countByStockLevelEquals(int stockLevel);

    @Query("SELECT COUNT(b) FROM Batch b WHERE b.expiryDate <= :expiryDate")
    long countExpiringSoon(@Param("expiryDate") LocalDate expiryDate);

    @Query("SELECT SUM(b.stockLevel * b.unitPrice) FROM Batch b")
    Double calculateTotalInventoryValue();
}

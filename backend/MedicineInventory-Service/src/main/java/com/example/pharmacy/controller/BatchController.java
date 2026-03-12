package com.example.pharmacy.controller;

import com.example.pharmacy.dto.BatchDTO;
import com.example.pharmacy.dto.DashboardMetricsDTO;
import com.example.pharmacy.service.BatchService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/medicines/{medicineId}/batches")
public class BatchController {

    @Autowired
    private BatchService batchService;
    
    @Autowired
    public BatchController(BatchService batchService) {
        this.batchService = batchService;
    }

    @PostMapping
    public BatchDTO addBatch(@PathVariable Long medicineId, @Valid @RequestBody BatchDTO batchDto) {
        return batchService.addBatchToMedicine(medicineId, batchDto);
    }
    
    @DeleteMapping("/{batchId}")
    public ResponseEntity<String> deleteBatch(
            @PathVariable Long medicineId,
            @PathVariable Long batchId) {
        
        batchService.deleteBatch(medicineId, batchId);
        return ResponseEntity.ok("Batch deleted successfully");
    }
    
    @GetMapping("/dashboard-metrics")
    public ResponseEntity<DashboardMetricsDTO> getDashboardMetrics() {
        DashboardMetricsDTO metrics = batchService.getDashboardMetrics();
        return ResponseEntity.ok(metrics);
    }

}

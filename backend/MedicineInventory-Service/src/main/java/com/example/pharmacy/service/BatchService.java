package com.example.pharmacy.service;

import com.example.pharmacy.dto.BatchDTO;
import com.example.pharmacy.dto.DashboardMetricsDTO;

public interface BatchService {
    BatchDTO addBatchToMedicine(Long medicineId, BatchDTO batchDto);
	void deleteBatch(Long medicineId, Long batchId);
	DashboardMetricsDTO getDashboardMetrics();
}

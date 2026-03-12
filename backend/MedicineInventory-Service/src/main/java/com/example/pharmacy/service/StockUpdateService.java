package com.example.pharmacy.service;

import com.example.pharmacy.dto.StockUpdateDTO;
import java.util.List;

public interface StockUpdateService {
    String updateStock(Long batchId, StockUpdateDTO dto);
    List<StockUpdateDTO> getUpdatesByBatch(Long batchId);
}

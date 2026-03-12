package com.example.pharmacy.service;

import com.example.pharmacy.dto.StockUpdateDTO;
import com.cognizant.entities.entity.Batch;
import com.cognizant.entities.entity.StockUpdate;
import com.example.pharmacy.repository.BatchRepository;
import com.example.pharmacy.repository.StockUpdateRepository;
import com.example.pharmacy.service.StockUpdateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockUpdateServiceImpl implements StockUpdateService {

    @Autowired
    private StockUpdateRepository stockUpdateRepository;

    @Autowired
    private BatchRepository batchRepository;

    @Override
    public String updateStock(Long batchId, StockUpdateDTO dto) {
        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new RuntimeException("Batch not found"));

        int newStockLevel = batch.getStockLevel() + dto.getChangeAmount();
        if (newStockLevel < 0) {
            throw new IllegalArgumentException("Stock cannot go below zero.");
        }

        batch.setStockLevel(newStockLevel);
        batchRepository.save(batch);

        StockUpdate update = new StockUpdate();
        update.setBatch(batch);
        update.setChangeAmount(dto.getChangeAmount());
        update.setReason(dto.getReason());
        update.setUpdateDate(LocalDateTime.now());

        stockUpdateRepository.save(update);

        return "Stock updated successfully.";
    }

    @Override
    public List<StockUpdateDTO> getUpdatesByBatch(Long batchId) {
        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new RuntimeException("Batch not found"));

        return stockUpdateRepository.findAll().stream()
                .filter(u -> u.getBatch().getBatchId().equals(batchId))
                .map(update -> {
                    StockUpdateDTO dto = new StockUpdateDTO();
                    dto.setUpdateId(update.getUpdateId());
                    dto.setBatchId(batchId);
                    dto.setChangeAmount(update.getChangeAmount());
                    dto.setReason(update.getReason());
                    dto.setUpdateDate(update.getUpdateDate().toString());
                    return dto;
                }).collect(Collectors.toList());
    }
}

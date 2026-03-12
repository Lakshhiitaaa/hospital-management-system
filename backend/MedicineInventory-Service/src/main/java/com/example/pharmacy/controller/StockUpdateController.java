package com.example.pharmacy.controller;

import com.example.pharmacy.dto.StockUpdateDTO;
import com.example.pharmacy.service.StockUpdateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/medicines/stock-updates")
public class StockUpdateController {

    @Autowired
    private StockUpdateService stockUpdateService;

    @PostMapping("/batch/{batchId}")
    public ResponseEntity<String> updateStock(@PathVariable Long batchId, @Valid @RequestBody StockUpdateDTO dto) {
        try {
            String result = stockUpdateService.updateStock(batchId, dto);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/batch/{batchId}")
    public ResponseEntity<List<StockUpdateDTO>> getUpdatesByBatch(@PathVariable Long batchId) {
        return ResponseEntity.ok(stockUpdateService.getUpdatesByBatch(batchId));
    }
}

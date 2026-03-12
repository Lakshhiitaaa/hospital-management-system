package com.example.pharmacy.controller;

import com.example.pharmacy.dto.StockUpdateDTO;
import com.cognizant.entities.entity.Batch;
import com.cognizant.entities.entity.StockUpdate;
import com.example.pharmacy.repository.BatchRepository;
import com.example.pharmacy.repository.StockUpdateRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StockUpdateController.class)
@AutoConfigureMockMvc(addFilters = false)
class StockUpdateControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private BatchRepository batchRepository;
    @MockBean private StockUpdateRepository stockUpdateRepository;
    @Autowired private ObjectMapper objectMapper;

    @Test
    void testUpdateStock() throws Exception {
        Batch batch = new Batch();
        batch.setBatchId(1L);
        batch.setStockLevel(10);

       // when(batchRepository.findById(1L)).thenReturn(Optional.of(batch));

        StockUpdateDTO dto = new StockUpdateDTO();
        dto.setBatchId(1L); // ✅ This line is necessary
        dto.setChangeAmount(5);
        dto.setReason("New Stock");

        mockMvc.perform(post("/api/stock-updates/batch/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Stock updated successfully."));
    }


    @Test
    void testGetUpdatesByBatch() throws Exception {
        Batch batch = new Batch();
        batch.setBatchId(1L);

        StockUpdate su = new StockUpdate();
        su.setUpdateId(1L);
        su.setBatch(batch);
        su.setChangeAmount(10);
        su.setReason("Added");
        su.setUpdateDate(LocalDateTime.now());

        //when(batchRepository.findById(1L)).thenReturn(Optional.of(batch));
        when(stockUpdateRepository.findAll()).thenReturn(List.of(su));

        mockMvc.perform(get("/api/stock-updates/batch/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].changeAmount").value(10));
    }
    
    @Test
    void testUpdateStock_InvalidBatch() throws Exception {
        StockUpdateDTO dto = new StockUpdateDTO();
        dto.setChangeAmount(10);
        dto.setReason("Test reason");

        when(batchRepository.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/stock-updates/batch/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().is4xxClientError());
    }

}

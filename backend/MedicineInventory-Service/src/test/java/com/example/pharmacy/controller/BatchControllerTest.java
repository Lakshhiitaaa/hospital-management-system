package com.example.pharmacy.controller;

import com.example.pharmacy.dto.BatchDTO;
import com.example.pharmacy.service.BatchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;

@WebMvcTest(BatchController.class)
@AutoConfigureMockMvc(addFilters = false)
class BatchControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private BatchService batchService;
    @Autowired private ObjectMapper objectMapper;

    @Test
    void testAddBatch() throws Exception {
        BatchDTO input = new BatchDTO();
        input.setBatchNumber("B123");
        input.setStockLevel(100);
        input.setUnitPrice(10.0);
        input.setExpiryDate(LocalDate.parse("2025-12-31")); // Fixed line

        BatchDTO output = new BatchDTO();
        output.setBatchId(1L);
        output.setBatchNumber("B123");

        when(batchService.addBatchToMedicine(eq(1L), any())).thenReturn(output);

        mockMvc.perform(post("/api/medicines/1/batches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.batchId").value(1));
    }


    @Test
    void testDeleteBatch() throws Exception {
        doNothing().when(batchService).deleteBatch(1L, 1L);

        mockMvc.perform(delete("/api/medicines/1/batches/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Batch deleted successfully"));
    }
    
    @Test
    void testAddDuplicateBatch() throws Exception {
        BatchDTO dto = new BatchDTO();
        dto.setBatchNumber("DUPLICATE");

        when(batchService.addBatchToMedicine(eq(1L), any()))
                .thenThrow(new RuntimeException("Batch already exists"));

        mockMvc.perform(post("/api/medicines/1/batches")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().is4xxClientError());
    }

    @Test
    void testDeleteNonExistentBatch() throws Exception {
        doThrow(new RuntimeException("Batch not found")).when(batchService).deleteBatch(1L, 999L);

        mockMvc.perform(delete("/api/medicines/1/batches/999"))
            .andExpect(status().is4xxClientError());
    }


}

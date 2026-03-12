package com.example.pharmacy.controller;

import com.example.pharmacy.dto.MedicineDTO;
import com.example.pharmacy.service.MedicineService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MedicineController.class)
@AutoConfigureMockMvc(addFilters = false)
class MedicineControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private MedicineService medicineService;
    @Autowired private ObjectMapper objectMapper;

    @Test
    void testGetAll() throws Exception {
        when(medicineService.getAllMedicines()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/medicines"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void testCreate() throws Exception {
        MedicineDTO input = new MedicineDTO();
        input.setName("Paracetamol");
        input.setDescription("Painkiller");
        input.setManufacturer("ABC Pharma");
        input.setDosage("500mg");
        input.setCategory("Tablet");
        input.setCommonUses("Fever");

        MedicineDTO saved = new MedicineDTO();
        saved.setMedicineId(1L);
        saved.setName("Paracetamol");

        when(medicineService.createMedicine(any())).thenReturn(saved);

        mockMvc.perform(post("/api/medicines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.medicineId").value(1));
    }

    @Test
    void testGetById() throws Exception {
        MedicineDTO dto = new MedicineDTO();
        dto.setMedicineId(1L);
        dto.setName("Paracetamol");

        when(medicineService.getMedicineDetails(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/medicines/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Paracetamol"));
    }

    @Test
    void testUpdate() throws Exception {
        MedicineDTO dto = new MedicineDTO();
        dto.setName("UpdatedName");
        dto.setDosage("500mg");
        dto.setManufacturer("Pharma Inc.");
        dto.setDescription("Pain reliever");
        dto.setCategory("Analgesic");
        dto.setCommonUses("Fever, headache");

        MedicineDTO updated = new MedicineDTO();
        updated.setMedicineId(1L);
        updated.setName("UpdatedName");
        updated.setDosage("500mg");
        updated.setManufacturer("Pharma Inc.");
        updated.setDescription("Pain reliever");
        updated.setCategory("Analgesic");
        updated.setCommonUses("Fever, headache");

        when(medicineService.updateMedicine(eq(1L), any())).thenReturn(updated);

        mockMvc.perform(put("/api/medicines/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("UpdatedName"));
    }


    @Test
    void testDelete() throws Exception {
        doNothing().when(medicineService).deleteMedicine(1L);

        mockMvc.perform(delete("/api/medicines/1"))
                .andExpect(status().isOk());
    }
    
    @Test
    void testGetMedicine_NotFound() throws Exception {
        when(medicineService.getMedicineDetails(999L)).thenThrow(new RuntimeException("Medicine not found"));

        mockMvc.perform(get("/api/medicines/999"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testCreateMedicine_MissingName() throws Exception {
        MedicineDTO input = new MedicineDTO();  // No name
        input.setManufacturer("ABC Pharma");

        mockMvc.perform(post("/api/medicines")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
            .andExpect(status().isBadRequest());
    }

}

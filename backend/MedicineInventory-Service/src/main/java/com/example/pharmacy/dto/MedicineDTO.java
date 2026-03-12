package com.example.pharmacy.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public class MedicineDTO {
    private Long medicineId;
    
    
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Manufacturer is required")
    private String manufacturer;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Dosage is required")
    private String dosage;

    @NotBlank(message = "Common uses are required")
    private String commonUses;
    
    
    private List<BatchDTO> batches;
    
    
	public Long getMedicineId() {
		return medicineId;
	}
	public void setMedicineId(Long medicineId) {
		this.medicineId = medicineId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDosage() {
		return dosage;
	}
	public void setDosage(String dosage) {
		this.dosage = dosage;
	}
	public String getCommonUses() {
		return commonUses;
	}
	public void setCommonUses(String commonUses) {
		this.commonUses = commonUses;
	}
	public List<BatchDTO> getBatches() {
		return batches;
	}
	public void setBatches(List<BatchDTO> batches) {
		this.batches = batches;
	}

    // getters and setters
    
}

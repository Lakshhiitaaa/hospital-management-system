package com.example.pharmacy.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BatchDTO {
    
	private Long batchId;
    
	@NotBlank(message = "Batch number is required")
	private String batchNumber;
    
    @NotNull(message = "Stock level is required")
    @Min(value = 0, message = "Stock level cannot be negative")
    private Integer stockLevel;
    
    @NotNull(message = "Unit price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Unit price must be greater than 0")
    private Double unitPrice;
    
    @NotNull(message = "Expiry date is required")
    @Future(message = "Expiry date must be in the future")
    private LocalDate expiryDate; 
    
    
	public Long getBatchId() {
		return batchId;
	}
	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public Integer getStockLevel() {
		return stockLevel;
	}
	public void setStockLevel(Integer stockLevel) {
		this.stockLevel = stockLevel;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public LocalDate getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

    
    
}

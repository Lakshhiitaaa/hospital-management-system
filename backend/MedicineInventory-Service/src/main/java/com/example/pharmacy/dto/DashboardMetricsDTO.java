package com.example.pharmacy.dto;

public class DashboardMetricsDTO {
    private long totalMedicines;
    private long outOfStockCount;
    private long expiringSoonCount;
    private double totalInventoryValue;

    public DashboardMetricsDTO() {}

    public DashboardMetricsDTO(long totalMedicines, long outOfStockCount, long expiringSoonCount, double totalInventoryValue) {
        this.totalMedicines = totalMedicines;
        this.outOfStockCount = outOfStockCount;
        this.expiringSoonCount = expiringSoonCount;
        this.totalInventoryValue = totalInventoryValue;
    }

	public long getTotalMedicines() {
		return totalMedicines;
	}

	public void setTotalMedicines(long totalMedicines) {
		this.totalMedicines = totalMedicines;
	}

	public long getOutOfStockCount() {
		return outOfStockCount;
	}

	public void setOutOfStockCount(long outOfStockCount) {
		this.outOfStockCount = outOfStockCount;
	}

	public long getExpiringSoonCount() {
		return expiringSoonCount;
	}

	public void setExpiringSoonCount(long expiringSoonCount) {
		this.expiringSoonCount = expiringSoonCount;
	}

	public double getTotalInventoryValue() {
		return totalInventoryValue;
	}

	public void setTotalInventoryValue(double totalInventoryValue) {
		this.totalInventoryValue = totalInventoryValue;
	}

    // Getters and setters
    
    
}

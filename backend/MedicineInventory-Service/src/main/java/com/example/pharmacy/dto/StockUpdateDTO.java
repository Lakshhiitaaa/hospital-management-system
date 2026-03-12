package com.example.pharmacy.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class StockUpdateDTO {
    private Long updateId;
    
    @NotNull(message = "Batch ID is required")
    private Long batchId;
    @NotNull(message = "Change amount is required")
    private Integer changeAmount;

    @NotBlank(message = "Reason is required")
    private String reason;
    private String updateDate;

    // Getters and setters

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public Integer getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(Integer changeAmount) {
        this.changeAmount = changeAmount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}

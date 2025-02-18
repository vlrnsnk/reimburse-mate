package com.vlrnsnk.reimbursemate.dto;

import com.vlrnsnk.reimbursemate.model.Reimbursement;

import java.math.BigDecimal;

public class ReimbursementDTO {
    private final Long id;
    private final String description;
    private final BigDecimal amount;
    private final Reimbursement.Status status;
    private final Long userId;
    private final String createdAt;
    private final String updatedAt;

    public ReimbursementDTO(
            Long id,
            String description,
            BigDecimal amount,
            String status,
            Long userId,
            String createdAt,
            String updatedAt
    ) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.status = Reimbursement.Status.valueOf(status);
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Reimbursement.Status getStatus() {
        return status;
    }

    public Long getUserId() {
        return userId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

}

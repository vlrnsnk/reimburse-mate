package com.vlrnsnk.reimbursemate.dto;

import com.vlrnsnk.reimbursemate.model.Reimbursement;

import java.math.BigDecimal;

public class ReimbursementDTO {
    private Long id;
    private String description;
    private BigDecimal amount;
    private Reimbursement.Status status;
    private Long userId;

    /**
     * Constructor for retrieving an existing reimbursement
     */
    public ReimbursementDTO(
            Long id,
            String description,
            BigDecimal amount,
            String status,
            Long userId
    ) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.status = Reimbursement.Status.valueOf(status);
        this.userId = userId;
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
}

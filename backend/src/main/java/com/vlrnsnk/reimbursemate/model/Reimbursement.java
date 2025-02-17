package com.vlrnsnk.reimbursemate.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Entity
public class Reimbursement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public Reimbursement() {
    }

    /**
     * Constructor for creating a new reimbursement
     */
    public Reimbursement(String description, BigDecimal amount, Status status, User user) {
        this.description = description;
        this.amount = amount;
        this.status = status != null ? status : Status.PENDING;
        this.user = user;
    }

    /**
     * Constructor for retrieving an existing reimbursement
     */
    public Reimbursement(
            Long id,
            String description,
            BigDecimal amount,
            Status status,
            User user
    ) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.status = status;
        this.user = user;
    }

    /**
     * Getters and Setters
     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", status=" + status +
                ", user=" + user +
                '}';
    }

    /**
     * Enum for the status of the reimbursement
     */
    public enum Status {
        PENDING,
        APPROVED,
        REJECTED,
    }
}

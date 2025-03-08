package com.vlrnsnk.reimbursemate.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "reimbursements")
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

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "approver_id", referencedColumnName = "id")
    private User approver;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Reimbursement() {
    }

    /**
     * Constructor for creating a new reimbursement
     */
    public Reimbursement(
            String description,
            BigDecimal amount,
            Status status,
            User user,
            User approver,
            String comment,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.description = description;
        this.amount = amount;
        this.status = status != null ? status : Status.PENDING;
        this.user = user;
        this.approver = approver;
        this.comment = comment;
        this.createdAt = Objects.requireNonNullElse(createdAt, LocalDateTime.now());
        this.updatedAt = Objects.requireNonNullElse(updatedAt, LocalDateTime.now());
    }

    /**
     * Constructor for retrieving an existing reimbursement
     */
    public Reimbursement(
            Long id,
            String description,
            BigDecimal amount,
            Status status,
            User user,
            User approver,
            String comment,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.status = status;
        this.user = user;
        this.approver = approver;
        this.comment = comment;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public User getApprover() {
        return approver;
    }

    public void setApprover(User approver) {
        this.approver = approver;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
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

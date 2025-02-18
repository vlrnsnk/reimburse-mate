package com.vlrnsnk.reimbursemate.repository;

import com.vlrnsnk.reimbursemate.model.Reimbursement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReimbursementRepository extends JpaRepository<Reimbursement, Long> {

    List<Reimbursement> findByStatus(Reimbursement.Status status);
    
}

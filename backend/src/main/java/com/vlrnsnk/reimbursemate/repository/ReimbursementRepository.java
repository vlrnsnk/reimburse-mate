package com.vlrnsnk.reimbursemate.repository;

import com.vlrnsnk.reimbursemate.model.Reimbursement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReimbursementRepository extends JpaRepository<Reimbursement, Long> {
}

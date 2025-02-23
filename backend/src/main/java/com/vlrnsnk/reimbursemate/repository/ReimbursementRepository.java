package com.vlrnsnk.reimbursemate.repository;

import com.vlrnsnk.reimbursemate.model.Reimbursement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReimbursementRepository extends JpaRepository<Reimbursement, Long> {

    /**
     * Find reimbursements by status
     *
     * @param status Reimbursement status
     * @return List of reimbursements with the given status
     */
    List<Reimbursement> findByStatus(Reimbursement.Status status);

    /**
     * Find reimbursements by user id
     *
     * @param userId User id
     * @return List of reimbursements with the given user id
     */
    List<Reimbursement> findByUserId(Long userId);

    /**
     * Find reimbursements by user id and status
     *
     * @param userId User id
     * @param status Reimbursement status
     * @return List of reimbursements with the given user id and status
     */
    List<Reimbursement> findByUserIdAndStatus(Long userId, Reimbursement.Status status);

    /**
     * Find reimbursements by user id
     *
     * @param  Approver id
     * @return List of reimbursements with the given approver id
     */
    void deleteByUserId(Long userId);

}

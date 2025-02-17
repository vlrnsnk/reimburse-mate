package com.vlrnsnk.reimbursemate.service;

import com.vlrnsnk.reimbursemate.model.Reimbursement;
import com.vlrnsnk.reimbursemate.repository.ReimbursementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReimbursementService {

    private final ReimbursementRepository reimbursementRepository;

    @Autowired
    public ReimbursementService(ReimbursementRepository reimbursementRepository) {
        this.reimbursementRepository = reimbursementRepository;
    }

    /**
     * Get all reimbursements
     *
     * @return List of all reimbursements
     */
    public List<Reimbursement> getAllReimbursements() {
        return reimbursementRepository.findAll();
    }
}

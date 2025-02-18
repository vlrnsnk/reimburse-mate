package com.vlrnsnk.reimbursemate.service;

import com.vlrnsnk.reimbursemate.dto.ReimbursementDTO;
import com.vlrnsnk.reimbursemate.mapper.ReimbursementMapper;
import com.vlrnsnk.reimbursemate.model.Reimbursement;
import com.vlrnsnk.reimbursemate.repository.ReimbursementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReimbursementService {

    private final ReimbursementRepository reimbursementRepository;
    private final ReimbursementMapper reimbursementMapper;

    @Autowired
    public ReimbursementService(
            ReimbursementRepository reimbursementRepository,
            ReimbursementMapper reimbursementMapper
    ) {
        this.reimbursementRepository = reimbursementRepository;
        this.reimbursementMapper = reimbursementMapper;
    }

    /**
     * Get all reimbursements
     *
     * @return List of all reimbursements
     */
    public List<ReimbursementDTO> getAllReimbursements() {
        List<Reimbursement> reimbursements = reimbursementRepository.findAll();

        return reimbursementMapper.toDTOList(reimbursements);
    }

    /**
     * Get reimbursements by status
     *
     * @param status Reimbursement status
     * @return List of reimbursements with the given status
     */
    public List<ReimbursementDTO> getReimbursementsByStatus(Reimbursement.Status status) {
        List<Reimbursement> reimbursements = reimbursementRepository.findByStatus(status);

        return reimbursementMapper.toDTOList(reimbursements);
    }

}

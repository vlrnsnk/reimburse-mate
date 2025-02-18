package com.vlrnsnk.reimbursemate.controller;

import com.vlrnsnk.reimbursemate.dto.ReimbursementDTO;
import com.vlrnsnk.reimbursemate.model.Reimbursement;
import com.vlrnsnk.reimbursemate.service.ReimbursementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reimbursements")
public class ReimbursementController {

    private final ReimbursementService reimbursementService;

    @Autowired
    public ReimbursementController(ReimbursementService reimbursementService) {
        this.reimbursementService = reimbursementService;
    }

    /**
     * Get all reimbursements or reimbursements by status
     *
     * @param status Reimbursement status
     * @return List of reimbursements
     */
    @GetMapping
    public ResponseEntity<List<ReimbursementDTO>> getAllReimbursements(@RequestParam(required = false) String status) {
        if (status == null) {
            List<ReimbursementDTO> reimbursements = reimbursementService.getAllReimbursements();

            return ResponseEntity.ok(reimbursements);
        }

        try {
            Reimbursement.Status reimbursementStatus = Reimbursement.Status.valueOf(status.toUpperCase());
            List<ReimbursementDTO> reimbursements = reimbursementService.getReimbursementsByStatus(reimbursementStatus);

            return ResponseEntity.ok(reimbursements);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}

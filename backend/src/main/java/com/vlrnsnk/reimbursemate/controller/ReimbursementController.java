package com.vlrnsnk.reimbursemate.controller;

import com.vlrnsnk.reimbursemate.aop.RequiresRole;
import com.vlrnsnk.reimbursemate.dto.ReimbursementDTO;
import com.vlrnsnk.reimbursemate.model.User;
import com.vlrnsnk.reimbursemate.service.ReimbursementService;
import com.vlrnsnk.reimbursemate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/reimbursements")
public class ReimbursementController {

    private final ReimbursementService reimbursementService;

    @Autowired
    public ReimbursementController(ReimbursementService reimbursementService, UserService userService) {
        this.reimbursementService = reimbursementService;
    }

    /**
     * Get all reimbursements or reimbursements by status
     *
     * @param status Reimbursement status
     * @return List of reimbursements
     */
    @RequiresRole(User.Role.MANAGER)
    @GetMapping
    public ResponseEntity<List<ReimbursementDTO>> getAllReimbursements(@RequestParam(required = false) String status) {
        try {
            List<ReimbursementDTO> reimbursements;

            if (status == null) {
                reimbursements = reimbursementService.getAllReimbursements();
            } else {
                reimbursements = reimbursementService.getReimbursementsByStatus(status);
            }

            return ResponseEntity.ok(reimbursements);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Resolve reimbursement (approve/reject)
     *
     * @param reimbursementId Reimbursement id
     * @param request Request body
     * @return Resolved reimbursement
     */
    @RequiresRole(User.Role.MANAGER)
    @PatchMapping("/{reimbursementId}")
    public ResponseEntity<ReimbursementDTO> resolveReimbursement(
            @PathVariable Long reimbursementId,
            @RequestBody Map<String, String> request
    ) {
        ReimbursementDTO reimbursementDTO = reimbursementService.resolveReimbursement(
                reimbursementId,
                request.get("status"),
                request.get("comment"),
                Long.valueOf(request.get("approverId"))
        );

        return ResponseEntity.ok(reimbursementDTO);
    }

    /**
     * Delete reimbursement by id
     *
     * @param reimbursementId Reimbursement id
     * @return No content if reimbursement is deleted, not found otherwise
     */
    @RequiresRole(User.Role.MANAGER)
    @DeleteMapping("/{reimbursementId}")
    public ResponseEntity<Void> deleteReimbursement(@PathVariable Long reimbursementId) {
        reimbursementService.deleteReimbursement(reimbursementId);

        return ResponseEntity.noContent().build();
    }
}

package com.vlrnsnk.reimbursemate.controller;

import com.vlrnsnk.reimbursemate.dto.ReimbursementDTO;
import com.vlrnsnk.reimbursemate.service.ReimbursementService;
import com.vlrnsnk.reimbursemate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/reimbursements")
@CrossOrigin(value = "http://localhost:5173", allowCredentials = "true")
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

}

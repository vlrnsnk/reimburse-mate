package com.vlrnsnk.reimbursemate.controller;

import com.vlrnsnk.reimbursemate.dto.ReimbursementDTO;
import com.vlrnsnk.reimbursemate.dto.UserDTO;
import com.vlrnsnk.reimbursemate.model.Reimbursement;
import com.vlrnsnk.reimbursemate.model.User;
import com.vlrnsnk.reimbursemate.service.ReimbursementService;
import com.vlrnsnk.reimbursemate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reimbursements")
public class ReimbursementController {

    private final ReimbursementService reimbursementService;
    private final UserService userService;

    @Autowired
    public ReimbursementController(ReimbursementService reimbursementService, UserService userService) {
        this.reimbursementService = reimbursementService;
        this.userService = userService;
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

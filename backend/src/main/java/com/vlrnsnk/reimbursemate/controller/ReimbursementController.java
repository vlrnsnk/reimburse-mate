package com.vlrnsnk.reimbursemate.controller;

import com.vlrnsnk.reimbursemate.aop.RequiresRole;
import com.vlrnsnk.reimbursemate.dto.ReimbursementDTO;
import com.vlrnsnk.reimbursemate.model.User;
import com.vlrnsnk.reimbursemate.service.ReimbursementService;
import com.vlrnsnk.reimbursemate.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/reimbursements")
@Tag(name = "Reimbursement Management", description = "Endpoints for managing reimbursements")
public class ReimbursementController {

    private final ReimbursementService reimbursementService;

    public ReimbursementController(ReimbursementService reimbursementService, UserService userService) {
        this.reimbursementService = reimbursementService;
    }

    /**
     * Get all reimbursements or reimbursements by status
     *
     * @param status Reimbursement status
     * @return List of reimbursements
     */
    @Operation(
            summary = "Get all reimbursements or filter by status",
            description = "Retrieves a list of all reimbursements or filters them by status. Requires MANAGER role."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReimbursementDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid status parameter",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Unauthorized access",
                    content = @Content)
    })
    @RequiresRole(User.Role.MANAGER)
    @GetMapping
    public ResponseEntity<List<ReimbursementDTO>> getAllReimbursements(
            @Parameter(description = "Status to filter reimbursements", example = "PENDING")
            @RequestParam(required = false) String status
    ) {
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
    @Operation(
            summary = "Resolve a reimbursement",
            description = "Approves or rejects a reimbursement request. Requires MANAGER role."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reimbursement resolved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReimbursementDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Unauthorized access",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Reimbursement not found",
                    content = @Content)
    })
    @RequiresRole(User.Role.MANAGER)
    @PatchMapping("/{reimbursementId}")
    public ResponseEntity<ReimbursementDTO> resolveReimbursement(
            @Parameter(description = "ID of the reimbursement to resolve", required = true, example = "1")
            @PathVariable Long reimbursementId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Resolution details",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = "{\"status\": \"APPROVED\", \"comment\": \"Approved as per policy\", \"approverId\": 123}")
                    )
            )
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
    @Operation(
            summary = "Delete a reimbursement",
            description = "Deletes a reimbursement by its ID. Requires MANAGER role."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reimbursement deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Unauthorized access",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Reimbursement not found",
                    content = @Content)
    })
    @RequiresRole(User.Role.MANAGER)
    @DeleteMapping("/{reimbursementId}")
    public ResponseEntity<Void> deleteReimbursement(
            @Parameter(description = "ID of the reimbursement to delete", required = true, example = "1")
            @PathVariable Long reimbursementId
    ) {
        System.out.println("Deleting reimbursement with id: " + reimbursementId);
        reimbursementService.deleteReimbursement(reimbursementId);

        return ResponseEntity.noContent().build();
    }

}

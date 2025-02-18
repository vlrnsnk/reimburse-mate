package com.vlrnsnk.reimbursemate.mapper;

import com.vlrnsnk.reimbursemate.dto.ReimbursementDTO;
import com.vlrnsnk.reimbursemate.model.Reimbursement;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReimbursementMapper {

    /**
     * Maps a Reimbursement entity to a ReimbursementDTO
     *
     * @param reimbursement the Reimbursement entity to be mapped
     * @return the ReimbursementDTO
     */
    public ReimbursementDTO toDTO(Reimbursement reimbursement) {
        return new ReimbursementDTO(
                reimbursement.getId(),
                reimbursement.getDescription(),
                reimbursement.getAmount(),
                reimbursement.getStatus().name(),
                reimbursement.getUser().getId(),
                reimbursement.getCreatedAt().toString(),
                reimbursement.getUpdatedAt().toString()
        );
    }

    /**
     * Maps a list of Reimbursement entities to a list of ReimbursementDTOs
     *
     * @param reimbursements the list of Reimbursement entities to be mapped
     * @return the list of ReimbursementDTOs
     */
    public List<ReimbursementDTO> toDTOList(List<Reimbursement> reimbursements) {
        return reimbursements.stream()
                .map(this::toDTO)
                .collect(java.util.stream.Collectors.toList());
    }

}

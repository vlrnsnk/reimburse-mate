package com.vlrnsnk.reimbursemate.mapper;

import com.vlrnsnk.reimbursemate.dto.ReimbursementDTO;
import com.vlrnsnk.reimbursemate.model.Reimbursement;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReimbursementMapper {

    /**
     * Maps a ReimbursementDTO to a Reimbursement entity
     *
     * @param reimbursementDTO the ReimbursementDTO to be mapped
     * @return the Reimbursement entity
     */
    public Reimbursement toEntity(ReimbursementDTO reimbursementDTO) {
        Reimbursement reimbursement = new Reimbursement();
        reimbursement.setDescription(reimbursementDTO.getDescription());
        reimbursement.setAmount(reimbursementDTO.getAmount());
        reimbursement.setStatus(Reimbursement.Status.valueOf(reimbursementDTO.getStatus().toString().toUpperCase()));
        reimbursement.setComment(reimbursementDTO.getComment());

        return reimbursement;
    }

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
                reimbursement.getApprover() == null ? null : reimbursement.getApprover().getId(),
                reimbursement.getComment(),
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

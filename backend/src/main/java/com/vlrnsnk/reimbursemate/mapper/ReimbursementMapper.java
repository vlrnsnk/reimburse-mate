package com.vlrnsnk.reimbursemate.mapper;

import com.vlrnsnk.reimbursemate.dto.ReimbursementDTO;
import com.vlrnsnk.reimbursemate.model.Reimbursement;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReimbursementMapper {

    public ReimbursementDTO toDTO(Reimbursement reimbursement) {
        return new ReimbursementDTO(
                reimbursement.getId(),
                reimbursement.getDescription(),
                reimbursement.getAmount(),
                reimbursement.getStatus().name(),
                reimbursement.getUser().getId()
        );
    }

    public List<ReimbursementDTO> toDTOList(List<Reimbursement> reimbursements) {
        return reimbursements.stream()
                .map(this::toDTO)
                .collect(java.util.stream.Collectors.toList());
    }

}

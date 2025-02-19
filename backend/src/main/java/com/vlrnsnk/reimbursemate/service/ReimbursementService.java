package com.vlrnsnk.reimbursemate.service;

import com.vlrnsnk.reimbursemate.dto.ReimbursementDTO;
import com.vlrnsnk.reimbursemate.exception.NotFoundException;
import com.vlrnsnk.reimbursemate.exception.ValidationException;
import com.vlrnsnk.reimbursemate.mapper.ReimbursementMapper;
import com.vlrnsnk.reimbursemate.mapper.UserMapper;
import com.vlrnsnk.reimbursemate.model.Reimbursement;
import com.vlrnsnk.reimbursemate.model.User;
import com.vlrnsnk.reimbursemate.repository.ReimbursementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ReimbursementService {

    private final ReimbursementRepository reimbursementRepository;
    private final ReimbursementMapper reimbursementMapper;

    @Autowired
    public ReimbursementService(
            ReimbursementRepository reimbursementRepository,
            ReimbursementMapper reimbursementMapper,
            UserMapper userMapper) {
        this.reimbursementRepository = reimbursementRepository;
        this.reimbursementMapper = reimbursementMapper;
    }

    // TODO: change returns to Optional

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

    /**
     * Get reimbursements by user id
     *
     * @param userId User id
     * @return List of reimbursements with the given user id
     */
    public List<ReimbursementDTO> getReimbursementsByUserId(Long userId) {
        List<Reimbursement> reimbursements = reimbursementRepository.findByUserId(userId);

        return reimbursementMapper.toDTOList(reimbursements);
    }

    /**
     * Get reimbursements by user id and status
     *
     * @param userId User id
     * @param status Reimbursement status
     * @return List of reimbursements with the given user id and status
     */
    public List<ReimbursementDTO> getReimbursementsByUserIdAndStatus(Long userId, Reimbursement.Status status) {
        List<Reimbursement> reimbursements = reimbursementRepository.findByUserIdAndStatus(userId, status);

        return reimbursementMapper.toDTOList(reimbursements);
    }

    /**
     * Get reimbursement by id
     *
     * @param reimbursementId Reimbursement id
     * @return Reimbursement with the given id
     */
    public Optional<Reimbursement> getReimbursementById(Long reimbursementId) {
        return reimbursementRepository.findById(reimbursementId);
    }

    /**
     * Create a new reimbursement
     *
     * @param user User entity
     * @param reimbursementDTO ReimbursementDTO
     * @return Created reimbursement
     */
    public ReimbursementDTO createReimbursement(User user, ReimbursementDTO reimbursementDTO) {
        Reimbursement reimbursement = reimbursementMapper.toEntity(reimbursementDTO);
        reimbursement.setUser(user);
        Reimbursement createdReimbursement = reimbursementRepository.save(reimbursement);

        return reimbursementMapper.toDTO(createdReimbursement);
    }

    public ReimbursementDTO updateReimbursement(
            Long userId,
            Long reimbursementId,
            Map<String, String> updates
    ) {
        Reimbursement reimbursement = reimbursementRepository.findById(reimbursementId)
                .orElseThrow(() -> new NotFoundException("Reimbursement not found"));

        if (reimbursement.getStatus() != Reimbursement.Status.PENDING || !reimbursement.getUser().getId().equals(userId)) {
            throw new ValidationException("Reimbursement is not in a pending state or does not belong to the user");
        }

        if (updates.containsKey("amount")) {
            String newAmount = updates.get("amount");

            if (newAmount != null && !newAmount.isEmpty()) {
                reimbursement.setAmount(new BigDecimal(newAmount));
            } else {
                throw new ValidationException("Invalid amount provided");
            }
        }

        if (updates.containsKey("description")) {
            String newDescription = updates.get("description");

            if (newDescription != null && !newDescription.isEmpty()) {
                reimbursement.setDescription(newDescription);
            } else {
                throw new ValidationException("Invalid description provided");
            }
        }

        Reimbursement updatedReimbursement = reimbursementRepository.save(reimbursement);

        return reimbursementMapper.toDTO(updatedReimbursement);
    }
}

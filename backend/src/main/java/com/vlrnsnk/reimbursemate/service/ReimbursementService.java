package com.vlrnsnk.reimbursemate.service;

import com.vlrnsnk.reimbursemate.dto.ReimbursementDTO;
import com.vlrnsnk.reimbursemate.exception.*;
import com.vlrnsnk.reimbursemate.mapper.ReimbursementMapper;
import com.vlrnsnk.reimbursemate.mapper.UserMapper;
import com.vlrnsnk.reimbursemate.model.Reimbursement;
import com.vlrnsnk.reimbursemate.model.User;
import com.vlrnsnk.reimbursemate.repository.ReimbursementRepository;
import com.vlrnsnk.reimbursemate.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReimbursementService {

    private final ReimbursementRepository reimbursementRepository;
    private final ReimbursementMapper reimbursementMapper;
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public ReimbursementService(
            ReimbursementRepository reimbursementRepository,
            ReimbursementMapper reimbursementMapper,
            UserMapper userMapper, UserService userService, UserRepository userRepository) {
        this.reimbursementRepository = reimbursementRepository;
        this.reimbursementMapper = reimbursementMapper;
        this.userService = userService;
        this.userRepository = userRepository;
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
    public List<ReimbursementDTO> getReimbursementsByStatus(String status) {
        Reimbursement.Status reimbursementStatus;

        try {
            reimbursementStatus = Reimbursement.Status.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidReimbursementStatusException("Invalid reimbursement status: " + status);
        }

        List<Reimbursement> reimbursements = reimbursementRepository.findByStatus(reimbursementStatus);

        return reimbursementMapper.toDTOList(reimbursements);
    }

    /**
     * Get reimbursements by user id
     *
     * @param userId User id
     * @return List of reimbursements with the given user id
     */
    public List<ReimbursementDTO> getReimbursementsByUserId(Long userId, HttpSession session) {
        Long sessionUserId = (Long) session.getAttribute("userId");

        if (!Objects.equals(sessionUserId, userId)) {
            throw new AuthorizationException("User is not authorized to view this user!");
        }

        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }

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
    public List<ReimbursementDTO> getReimbursementsByUserIdAndStatus(Long userId, String status, HttpSession session) {
        Long sessionUserId = (Long) session.getAttribute("userId");

        if (!Objects.equals(sessionUserId, userId)) {
            throw new AuthorizationException("User is not authorized to view this user!");
        }

        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }

        try {
            Reimbursement.Status reimbursementStatus = Reimbursement.Status.valueOf(status.toUpperCase());
            List<Reimbursement> reimbursements = reimbursementRepository.findByUserIdAndStatus(userId, reimbursementStatus);

            return reimbursementMapper.toDTOList(reimbursements);
        } catch (IllegalArgumentException e) {
            throw new InvalidReimbursementStatusException("Invalid reimbursement status: " + status);
        }
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
     * @param userId User ID
     * @param reimbursementDTO ReimbursementDTO
     * @return Created reimbursement
     */
    public ReimbursementDTO createReimbursement(Long userId, ReimbursementDTO reimbursementDTO, HttpSession session) {
        Long sessionUserId = (Long) session.getAttribute("userId");

        if (!Objects.equals(sessionUserId, userId)) {
            throw new AuthorizationException("User is not authorized to view this user!");
        }

        User user = userService.getUserEntityById(userId);
        Reimbursement reimbursement = reimbursementMapper.toEntity(reimbursementDTO);
        reimbursement.setUser(user);
        Reimbursement createdReimbursement = reimbursementRepository.save(reimbursement);

        return reimbursementMapper.toDTO(createdReimbursement);
    }

    /**
     * Update a reimbursement
     *
     * @param userId User ID
     * @param reimbursementId Reimbursement ID
     * @param updates Updates
     * @return Updated reimbursement
     */
    public ReimbursementDTO updateReimbursement(
            Long userId,
            Long reimbursementId,
            Map<String, String> updates,
            HttpSession session
    ) {
        Long sessionUserId = (Long) session.getAttribute("userId");

        if (!Objects.equals(sessionUserId, userId)) {
            throw new AuthorizationException("User is not authorized to view this user!");
        }

        Reimbursement reimbursement = reimbursementRepository.findById(reimbursementId)
                .orElseThrow(() -> new ReimbursementNotFoundException("Reimbursement with ID " + reimbursementId + " not found."));

        User.Role userRole = (User.Role) session.getAttribute("role");

        if (!(userRole.equals(User.Role.MANAGER) ||
                reimbursement.getStatus() == Reimbursement.Status.PENDING && reimbursement.getUser().getId().equals(userId))) {
//        if (reimbursement.getStatus() != Reimbursement.Status.PENDING || !reimbursement.getUser().getId().equals(userId)) {
            throw new InvalidReimbursementStatusException("Reimbursement is not in a pending state or does not belong to the user");
        }

        if (updates.containsKey("amount")) {
            String newAmount = updates.get("amount");

            if (newAmount == null || newAmount.isEmpty()) {
                throw new InvalidReimbursementStatusException("Invalid amount provided");
            }

            reimbursement.setAmount(new BigDecimal(newAmount));
        }

        if (updates.containsKey("description")) {
            String newDescription = updates.get("description");

            if (newDescription == null || newDescription.isEmpty()) {
                throw new InvalidReimbursementStatusException("Invalid description provided");
            }

            reimbursement.setDescription(newDescription);
        }

        Reimbursement updatedReimbursement = reimbursementRepository.save(reimbursement);

        return reimbursementMapper.toDTO(updatedReimbursement);
    }

    /**
     * Resolve a reimbursement
     *
     * @param reimbursementId Reimbursement id
     * @param status Reimbursement status
     * @param comment Comment
     * @param approverId Approver id
     * @return Resolved reimbursement
     */
    public ReimbursementDTO resolveReimbursement(Long reimbursementId, String status, String comment, Long approverId) {
        User approver = userService.getUserEntityById(approverId);

        if (approver.getRole() != User.Role.MANAGER) {
            throw new InvalidUserRoleException("Approver does not have the required role: MANAGER");
        }

        Reimbursement.Status reimbursementStatus;

        try {
            reimbursementStatus = Reimbursement.Status.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidReimbursementStatusException("Invalid reimbursement status: " + status);
        }

        Reimbursement reimbursement = reimbursementRepository.findById(reimbursementId)
                .orElseThrow(() -> new ReimbursementNotFoundException("Reimbursement not found with ID: " + reimbursementId));

        reimbursement.setStatus(reimbursementStatus);
        reimbursement.setComment(comment);
        reimbursement.setApprover(approver);

        Reimbursement updatedReimbursement = reimbursementRepository.save(reimbursement);

        return reimbursementMapper.toDTO(updatedReimbursement);
    }

    /**
     * Delete reimbursement by id
     *
     * @param reimbursementId Reimbursement id
     */
    public void deleteReimbursement(Long reimbursementId) {
        Reimbursement reimbursement = reimbursementRepository.findById(reimbursementId)
                .orElseThrow(() -> new ReimbursementNotFoundException("Reimbursement not found with ID: " + reimbursementId));

        reimbursementRepository.delete(reimbursement);
    }

    /**
     * Delete reimbursement by user id and reimbursement id
     *
     * @param userId User id
     * @param reimbursementId Reimbursement id
     */
    public void deleteReimbursementByUserIdAndReimbursementId(
            Long userId,
            Long reimbursementId,
            HttpSession session
    ) {
        Long sessionUserId = (Long) session.getAttribute("userId");

        if (!Objects.equals(sessionUserId, userId)) {
            throw new AuthorizationException("User is not authorized to view this user!");
        }

        Reimbursement reimbursement = reimbursementRepository.findById(reimbursementId)
                .orElseThrow(() -> new ReimbursementNotFoundException("Reimbursement not found with ID: " + reimbursementId));

        reimbursementRepository.delete(reimbursement);
    }

}

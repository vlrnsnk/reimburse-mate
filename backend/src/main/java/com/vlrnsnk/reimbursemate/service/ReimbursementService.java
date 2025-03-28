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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReimbursementService {

    private static final Logger logger = LoggerFactory.getLogger(ReimbursementService.class);

    private final ReimbursementRepository reimbursementRepository;
    private final ReimbursementMapper reimbursementMapper;
    private final UserService userService;
    private final UserRepository userRepository;

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
        logger.info("Fetching all reimbursements");
        List<Reimbursement> reimbursements = reimbursementRepository.findAll();
        logger.info("Found {} reimbursements", reimbursements.size());
        return reimbursementMapper.toDTOList(reimbursements);
    }

    /**
     * Get reimbursements by status
     *
     * @param status Reimbursement status
     * @return List of reimbursements with the given status
     */
    public List<ReimbursementDTO> getReimbursementsByStatus(String status) {
        logger.info("Fetching reimbursements with status: {}", status);
        Reimbursement.Status reimbursementStatus;

        try {
            reimbursementStatus = Reimbursement.Status.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            logger.error("Invalid reimbursement status: {}", status, e);
            throw new InvalidReimbursementStatusException("Invalid reimbursement status: " + status);
        }

        List<Reimbursement> reimbursements = reimbursementRepository.findByStatus(reimbursementStatus);
        logger.info("Found {} reimbursements with status: {}", reimbursements.size(), status);
        return reimbursementMapper.toDTOList(reimbursements);
    }

    /**
     * Get reimbursements by user id
     *
     * @param userId User id
     * @return List of reimbursements with the given user id
     */
    public List<ReimbursementDTO> getReimbursementsByUserId(Long userId, HttpSession session) {
        logger.info("Fetching reimbursements for user ID: {}", userId);
        Long sessionUserId = (Long) session.getAttribute("userId");

        if (!Objects.equals(sessionUserId, userId)) {
            logger.warn("User with ID {} is not authorized to view reimbursements for user ID: {}", sessionUserId, userId);
            throw new AuthorizationException("User is not authorized to view this user!");
        }

        if (!userRepository.existsById(userId)) {
            logger.warn("User not found with ID: {}", userId);
            throw new UserNotFoundException("User not found with ID: " + userId);
        }

        List<Reimbursement> reimbursements = reimbursementRepository.findByUserId(userId);
        logger.info("Found {} reimbursements for user ID: {}", reimbursements.size(), userId);
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
        logger.info("Fetching reimbursements for user ID: {} with status: {}", userId, status);
        Long sessionUserId = (Long) session.getAttribute("userId");

        if (!Objects.equals(sessionUserId, userId)) {
            logger.warn("User with ID {} is not authorized to view reimbursements for user ID: {}", sessionUserId, userId);
            throw new AuthorizationException("User is not authorized to view this user!");
        }

        if (!userRepository.existsById(userId)) {
            logger.warn("User not found with ID: {}", userId);
            throw new UserNotFoundException("User not found with ID: " + userId);
        }

        try {
            Reimbursement.Status reimbursementStatus = Reimbursement.Status.valueOf(status.toUpperCase());
            List<Reimbursement> reimbursements = reimbursementRepository.findByUserIdAndStatus(userId, reimbursementStatus);
            logger.info("Found {} reimbursements for user ID: {} with status: {}", reimbursements.size(), userId, status);
            return reimbursementMapper.toDTOList(reimbursements);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid reimbursement status: {}", status, e);
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
        logger.info("Fetching reimbursement by ID: {}", reimbursementId);
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
        logger.info("Creating reimbursement for user ID: {}", userId);
        Long sessionUserId = (Long) session.getAttribute("userId");

        if (!Objects.equals(sessionUserId, userId)) {
            logger.warn("User with ID {} is not authorized to create reimbursements for user ID: {}", sessionUserId, userId);
            throw new AuthorizationException("User is not authorized to view this user!");
        }

        User user = userService.getUserEntityById(userId);
        Reimbursement reimbursement = reimbursementMapper.toEntity(reimbursementDTO);
        reimbursement.setUser(user);
        Reimbursement createdReimbursement = reimbursementRepository.save(reimbursement);
        logger.info("Reimbursement created successfully with ID: {}", createdReimbursement.getId());
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
        logger.info("Updating reimbursement with ID: {} for user ID: {}", reimbursementId, userId);
        Long sessionUserId = (Long) session.getAttribute("userId");

        if (!Objects.equals(sessionUserId, userId)) {
            logger.warn("User with ID {} is not authorized to update reimbursements for user ID: {}", sessionUserId, userId);
            throw new AuthorizationException("User is not authorized to view this user!");
        }

        Reimbursement reimbursement = reimbursementRepository.findById(reimbursementId)
                .orElseThrow(() -> {
                    logger.warn("Reimbursement not found with ID: {}", reimbursementId);
                    return new ReimbursementNotFoundException("Reimbursement with ID " + reimbursementId + " not found.");
                });

        User.Role userRole = (User.Role) session.getAttribute("role");

        if (!(userRole.equals(User.Role.MANAGER) ||
                reimbursement.getStatus() == Reimbursement.Status.PENDING && reimbursement.getUser().getId().equals(userId))) {
            logger.warn("Reimbursement with ID {} is not in a pending state or does not belong to the user", reimbursementId);
            throw new InvalidReimbursementStatusException("Reimbursement is not in a pending state or does not belong to the user");
        }

        if (updates.containsKey("amount")) {
            String newAmount = updates.get("amount");

            if (newAmount == null || newAmount.isEmpty()) {
                logger.warn("Invalid amount provided for reimbursement ID: {}", reimbursementId);
                throw new InvalidReimbursementStatusException("Invalid amount provided");
            }

            reimbursement.setAmount(new BigDecimal(newAmount));
        }

        if (updates.containsKey("description")) {
            String newDescription = updates.get("description");

            if (newDescription == null || newDescription.isEmpty()) {
                logger.warn("Invalid description provided for reimbursement ID: {}", reimbursementId);
                throw new InvalidReimbursementStatusException("Invalid description provided");
            }

            reimbursement.setDescription(newDescription);
        }

        Reimbursement updatedReimbursement = reimbursementRepository.save(reimbursement);
        logger.info("Reimbursement updated successfully with ID: {}", reimbursementId);
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
        logger.info("Resolving reimbursement with ID: {}", reimbursementId);
        User approver = userService.getUserEntityById(approverId);

        if (approver.getRole() != User.Role.MANAGER) {
            logger.warn("Approver with ID {} does not have the required role: MANAGER", approverId);
            throw new InvalidUserRoleException("Approver does not have the required role: MANAGER");
        }

        Reimbursement.Status reimbursementStatus;

        try {
            reimbursementStatus = Reimbursement.Status.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            logger.error("Invalid reimbursement status: {}", status, e);
            throw new InvalidReimbursementStatusException("Invalid reimbursement status: " + status);
        }

        Reimbursement reimbursement = reimbursementRepository.findById(reimbursementId)
                .orElseThrow(() -> {
                    logger.warn("Reimbursement not found with ID: {}", reimbursementId);
                    return new ReimbursementNotFoundException("Reimbursement not found with ID: " + reimbursementId);
                });

        reimbursement.setStatus(reimbursementStatus);
        reimbursement.setComment(comment);
        reimbursement.setApprover(approver);

        Reimbursement updatedReimbursement = reimbursementRepository.save(reimbursement);
        logger.info("Reimbursement resolved successfully with ID: {}", reimbursementId);
        return reimbursementMapper.toDTO(updatedReimbursement);
    }

    /**
     * Delete reimbursement by id
     *
     * @param reimbursementId Reimbursement id
     */
    public void deleteReimbursement(Long reimbursementId) {
        logger.info("Deleting reimbursement with ID: {}", reimbursementId);
        Reimbursement reimbursement = reimbursementRepository.findById(reimbursementId)
                .orElseThrow(() -> {
                    logger.warn("Reimbursement not found with ID: {}", reimbursementId);
                    return new ReimbursementNotFoundException("Reimbursement not found with ID: " + reimbursementId);
                });

        reimbursementRepository.delete(reimbursement);
        logger.info("Reimbursement deleted successfully with ID: {}", reimbursementId);
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
        logger.info("Deleting reimbursement with ID: {} for user ID: {}", reimbursementId, userId);
        Long sessionUserId = (Long) session.getAttribute("userId");

        if (!Objects.equals(sessionUserId, userId)) {
            logger.warn("User with ID {} is not authorized to delete reimbursements for user ID: {}", sessionUserId, userId);
            throw new AuthorizationException("User is not authorized to view this user!");
        }

        Reimbursement reimbursement = reimbursementRepository.findById(reimbursementId)
                .orElseThrow(() -> {
                    logger.warn("Reimbursement not found with ID: {}", reimbursementId);
                    return new ReimbursementNotFoundException("Reimbursement not found with ID: " + reimbursementId);
                });

        reimbursementRepository.delete(reimbursement);
        logger.info("Reimbursement deleted successfully with ID: {}", reimbursementId);
    }
}
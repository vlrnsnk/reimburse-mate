package com.vlrnsnk.reimbursemate.service;

import com.vlrnsnk.reimbursemate.dto.ReimbursementDTO;
import com.vlrnsnk.reimbursemate.exception.*;
import com.vlrnsnk.reimbursemate.mapper.ReimbursementMapper;
import com.vlrnsnk.reimbursemate.model.Reimbursement;
import com.vlrnsnk.reimbursemate.model.User;
import com.vlrnsnk.reimbursemate.repository.ReimbursementRepository;
import com.vlrnsnk.reimbursemate.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReimbursementServiceTest {

    @Mock
    private ReimbursementRepository reimbursementRepository;

    @Mock
    private ReimbursementMapper reimbursementMapper;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private HttpSession session;

    @InjectMocks
    private ReimbursementService reimbursementService;

    private Reimbursement reimbursement;
    private ReimbursementDTO reimbursementDTO;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setRole(User.Role.MANAGER);

        reimbursement = new Reimbursement();
        reimbursement.setId(1L);
        reimbursement.setDescription("Travel expenses");
        reimbursement.setAmount(new BigDecimal("100.00"));
        reimbursement.setStatus(Reimbursement.Status.PENDING);
        reimbursement.setUser(user);

        reimbursementDTO = new ReimbursementDTO();
        reimbursementDTO.setId(1L);
        reimbursementDTO.setDescription("Travel expenses");
        reimbursementDTO.setAmount(String.valueOf(new BigDecimal("100.00")));
        reimbursementDTO.setStatus(Reimbursement.Status.PENDING);
        reimbursementDTO.setUserId(1L);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testGetAllReimbursements() {
        // Mock behavior
        when(reimbursementRepository.findAll()).thenReturn(List.of(reimbursement));
        when(reimbursementMapper.toDTOList(any(List.class))).thenReturn(List.of(reimbursementDTO));

        // Test
        List<ReimbursementDTO> result = reimbursementService.getAllReimbursements();

        // Assertions
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(reimbursementRepository, times(1)).findAll();
    }

    @SuppressWarnings("unchecked")
    @Test
    void testGetReimbursementsByStatus_Success() {
        // Mock behavior
        when(reimbursementRepository.findByStatus(Reimbursement.Status.PENDING)).thenReturn(List.of(reimbursement));
        when(reimbursementMapper.toDTOList(any(List.class))).thenReturn(List.of(reimbursementDTO));

        // Test
        List<ReimbursementDTO> result = reimbursementService.getReimbursementsByStatus("PENDING");

        // Assertions
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(reimbursementRepository, times(1)).findByStatus(Reimbursement.Status.PENDING);
    }

    @Test
    void testGetReimbursementsByStatus_InvalidStatus() {
        // Test and Assertions
        assertThrows(InvalidReimbursementStatusException.class, () -> reimbursementService.getReimbursementsByStatus("INVALID"));
        verify(reimbursementRepository, never()).findByStatus(any());
    }

    @SuppressWarnings("unchecked")
    @Test
    void testGetReimbursementsByUserId_Success() {
        // Mock behavior
        when(session.getAttribute("userId")).thenReturn(1L);
        when(userRepository.existsById(1L)).thenReturn(true);
        when(reimbursementRepository.findByUserId(1L)).thenReturn(List.of(reimbursement));
        when(reimbursementMapper.toDTOList(any(List.class))).thenReturn(List.of(reimbursementDTO));

        // Test
        List<ReimbursementDTO> result = reimbursementService.getReimbursementsByUserId(1L, session);

        // Assertions
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(reimbursementRepository, times(1)).findByUserId(1L);
    }

    @Test
    void testGetReimbursementsByUserId_Unauthorized() {
        // Mock behavior
        when(session.getAttribute("userId")).thenReturn(2L);

        // Test and Assertions
        assertThrows(AuthorizationException.class, () -> reimbursementService.getReimbursementsByUserId(1L, session));
        verify(reimbursementRepository, never()).findByUserId(any());
    }

    @SuppressWarnings("unchecked")
    @Test
    void testGetReimbursementsByUserIdAndStatus_Success() {
        // Mock behavior
        when(session.getAttribute("userId")).thenReturn(1L);
        when(userRepository.existsById(1L)).thenReturn(true);
        when(reimbursementRepository.findByUserIdAndStatus(1L, Reimbursement.Status.PENDING)).thenReturn(List.of(reimbursement));
        when(reimbursementMapper.toDTOList(any(List.class))).thenReturn(List.of(reimbursementDTO));

        // Test
        List<ReimbursementDTO> result = reimbursementService.getReimbursementsByUserIdAndStatus(1L, "PENDING", session);

        // Assertions
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(reimbursementRepository, times(1)).findByUserIdAndStatus(1L, Reimbursement.Status.PENDING);
    }

    @Test
    void testCreateReimbursement_Success() {
        // Mock behavior
        when(session.getAttribute("userId")).thenReturn(1L);
        when(userService.getUserEntityById(1L)).thenReturn(user);
        when(reimbursementMapper.toEntity(any(ReimbursementDTO.class))).thenReturn(reimbursement);
        when(reimbursementRepository.save(any(Reimbursement.class))).thenReturn(reimbursement);
        when(reimbursementMapper.toDTO(any(Reimbursement.class))).thenReturn(reimbursementDTO);

        // Test
        ReimbursementDTO result = reimbursementService.createReimbursement(1L, reimbursementDTO, session);

        // Assertions
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(reimbursementRepository, times(1)).save(any(Reimbursement.class));
    }

    @Test
    void testUpdateReimbursement_Success() {
        // Mock behavior
        when(session.getAttribute("userId")).thenReturn(1L);
        when(session.getAttribute("role")).thenReturn(User.Role.EMPLOYEE);
        when(reimbursementRepository.findById(1L)).thenReturn(Optional.of(reimbursement));
        when(reimbursementRepository.save(any(Reimbursement.class))).thenReturn(reimbursement);
        when(reimbursementMapper.toDTO(any(Reimbursement.class))).thenReturn(reimbursementDTO);

        // Test
        Map<String, String> updates = new HashMap<>();
        updates.put("description", "Updated description");
        ReimbursementDTO result = reimbursementService.updateReimbursement(1L, 1L, updates, session);

        // Assertions
        assertNotNull(result);
        assertEquals("Updated description", reimbursement.getDescription());
        verify(reimbursementRepository, times(1)).save(any(Reimbursement.class));
    }

    @Test
    void testResolveReimbursement_Success() {
        // Mock behavior
        when(userService.getUserEntityById(1L)).thenReturn(user);
        when(reimbursementRepository.findById(1L)).thenReturn(Optional.of(reimbursement));
        when(reimbursementRepository.save(any(Reimbursement.class))).thenReturn(reimbursement);
        when(reimbursementMapper.toDTO(any(Reimbursement.class))).thenReturn(reimbursementDTO);

        // Test
        ReimbursementDTO result = reimbursementService.resolveReimbursement(1L, "APPROVED", "Approved", 1L);

        // Assertions
        assertNotNull(result);
        assertEquals(Reimbursement.Status.APPROVED, reimbursement.getStatus());
        verify(reimbursementRepository, times(1)).save(any(Reimbursement.class));
    }

    @Test
    void testDeleteReimbursement_Success() {
        // Mock behavior
        when(reimbursementRepository.findById(1L)).thenReturn(Optional.of(reimbursement));

        // Test
        reimbursementService.deleteReimbursement(1L);

        // Assertions
        verify(reimbursementRepository, times(1)).delete(any(Reimbursement.class));
    }

}
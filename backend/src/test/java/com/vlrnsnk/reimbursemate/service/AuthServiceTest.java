package com.vlrnsnk.reimbursemate.service;

import com.vlrnsnk.reimbursemate.dto.LoginRequestDTO;
import com.vlrnsnk.reimbursemate.dto.UserDTO;
import com.vlrnsnk.reimbursemate.exception.MissingRequiredFieldsException;
import com.vlrnsnk.reimbursemate.exception.UserCreationException;
import com.vlrnsnk.reimbursemate.exception.UserNotFoundException;
import com.vlrnsnk.reimbursemate.mapper.UserMapper;
import com.vlrnsnk.reimbursemate.model.User;
import com.vlrnsnk.reimbursemate.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private HttpSession session;

    @InjectMocks
    private AuthService authService;

    private User user;
    private UserDTO userDTO;
    private LoginRequestDTO loginRequestDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setRole(User.Role.EMPLOYEE);

        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUsername("testuser");
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setRole(User.Role.EMPLOYEE);

        loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUsername("testuser");
        loginRequestDTO.setPassword("password");
    }

    @Test
    void testRegisterUser_Success() {
        // Mock behavior
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toDTO(any(User.class))).thenReturn(userDTO);

        // Test
        UserDTO result = authService.registerUser(user);

        // Assertions
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(userRepository, times(1)).findByUsername(any(String.class));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUser_UsernameAlreadyExists() {
        // Mock behavior
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user));

        // Test and Assertions
        assertThrows(UserCreationException.class, () -> authService.registerUser(user));
        verify(userRepository, times(1)).findByUsername(any(String.class));
        verify(userRepository, never()).save(any(User.class));
    }

//    @Test
//    void testLoginUser_Success() {
//        // Mock behavior
//        when(userRepository.findByUsernameAndPassword(any(String.class), any(String.class)))
//                .thenReturn(Optional.of(user));
//        when(userMapper.toDTO(any(User.class))).thenReturn(userDTO);
//
//        // Test
//        UserDTO result = authService.loginUser(loginRequestDTO, session);
//
//        // Assertions
//        assertNotNull(result);
//        assertEquals("testuser", result.getUsername());
//        verify(session, times(1)).setAttribute("userId", user.getId());
//        verify(session, times(1)).setAttribute("username", user.getUsername());
//        verify(session, times(1)).setAttribute("firstName", user.getFirstName());
//        verify(session, times(1)).setAttribute("lastName", user.getLastName());
//        verify(session, times(1)).setAttribute("role", user.getRole());
//    }

//    @Test
//    void testLoginUser_InvalidUsernameOrPassword() {
//        // Mock behavior
//        when(userRepository.findByUsernameAndPassword(any(String.class), any(String.class)))
//                .thenReturn(Optional.empty());
//
//        // Test and Assertions
//        assertThrows(UserNotFoundException.class, () -> authService.loginUser(loginRequestDTO, session));
//        verify(userRepository, times(1)).findByUsernameAndPassword(any(String.class), any(String.class));
//    }

//    @Test
//    void testLoginUser_MissingUsername() {
//        // Setup
//        loginRequestDTO.setUsername(null);
//
//        // Test and Assertions
//        assertThrows(MissingRequiredFieldsException.class, () -> authService.loginUser(loginRequestDTO, session));
//        verify(userRepository, never()).findByUsernameAndPassword(any(String.class), any(String.class));
//    }

//    @Test
//    void testLoginUser_MissingPassword() {
//        // Setup
//        loginRequestDTO.setPassword(null);
//
//        // Test and Assertions
//        assertThrows(MissingRequiredFieldsException.class, () -> authService.loginUser(loginRequestDTO, session));
//        verify(userRepository, never()).findByUsernameAndPassword(any(String.class), any(String.class));
//    }

    @Test
    void testLogoutUser_Success() {
        // Mock behavior
        when(session.getAttribute("username")).thenReturn("testuser");

        // Test
        authService.logoutUser(session);

        // Assertions
        verify(session, times(1)).invalidate();
    }

}
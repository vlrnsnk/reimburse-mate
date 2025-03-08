package com.vlrnsnk.reimbursemate.service;

import com.vlrnsnk.reimbursemate.dto.UserDTO;
import com.vlrnsnk.reimbursemate.dto.UserProfileUpdateDTO;
import com.vlrnsnk.reimbursemate.exception.AuthorizationException;
import com.vlrnsnk.reimbursemate.exception.InvalidUserRoleException;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private HttpSession session;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("john_doe");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setRole(User.Role.EMPLOYEE);

        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUsername("john_doe");
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setRole(User.Role.EMPLOYEE);
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        when(userMapper.toDTOList(anyList())).thenReturn(Arrays.asList(userDTO));

        // Act
        List<UserDTO> result = userService.getAllUsers();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userDTO, result.get(0));
        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(1)).toDTOList(anyList());
    }

    @Test
    void testGetUserById_Success() {
        // Arrange
        when(session.getAttribute("userId")).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        // Act
        UserDTO result = userService.getUserById(1L, session);

        // Assert
        assertNotNull(result);
        assertEquals(userDTO, result);
        verify(userRepository, times(1)).findById(1L);
        verify(userMapper, times(1)).toDTO(user);
    }

    @Test
    void testGetUserById_Unauthorized() {
        // Arrange
        when(session.getAttribute("userId")).thenReturn(2L);

        // Act & Assert
        assertThrows(AuthorizationException.class, () -> userService.getUserById(1L, session));
        verify(userRepository, never()).findById(anyLong());
    }

    @Test
    void testGetUserById_NotFound() {
        // Arrange
        when(session.getAttribute("userId")).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L, session));
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUserEntityById_Success() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        User result = userService.getUserEntityById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(user, result);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUserEntityById_NotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.getUserEntityById(1L));
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateUser_Success() {
        // Arrange
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        // Act
        UserDTO result = userService.createUser(user);

        // Assert
        assertNotNull(result);
        assertEquals(userDTO, result);
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).toDTO(user);
    }

    @Test
    void testCreateUser_Failure() {
        // Arrange
        when(userRepository.save(user)).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        assertThrows(UserCreationException.class, () -> userService.createUser(user));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdateUserRole_Success() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        // Act
        UserDTO result = userService.updateUserRole(1L, "MANAGER");

        // Assert
        assertNotNull(result);
        assertEquals(userDTO, result);
        assertEquals(User.Role.MANAGER, user.getRole());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).toDTO(user);
    }

    @Test
    void testUpdateUserRole_InvalidRole() {
        // Act & Assert
        assertThrows(InvalidUserRoleException.class, () -> userService.updateUserRole(1L, "INVALID_ROLE"));
        verify(userRepository, never()).findById(anyLong());
    }

    @Test
    void testUpdateUserRole_NotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.updateUserRole(1L, "MANAGER"));
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteUser_Success() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        userService.deleteUser(1L);

        // Assert
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void testDeleteUser_NotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(1L));
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, never()).delete(any());
    }

    @Test
    void testUpdateUserProfile_Success() {
        // Arrange
        UserProfileUpdateDTO profileUpdateDTO = new UserProfileUpdateDTO();
        profileUpdateDTO.setFirstName("Jane");
        profileUpdateDTO.setLastName("Smith");
        profileUpdateDTO.setUsername("jane_smith");

        when(session.getAttribute("userId")).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        // Act
        UserDTO result = userService.updateUserProfile(1L, profileUpdateDTO, session);

        // Assert
        assertNotNull(result);
        assertEquals(userDTO, result);
        assertEquals("Jane", user.getFirstName());
        assertEquals("Smith", user.getLastName());
        assertEquals("jane_smith", user.getUsername());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).toDTO(user);
    }

    @Test
    void testUpdateUserProfile_Unauthorized() {
        // Arrange
        when(session.getAttribute("userId")).thenReturn(2L);

        // Act & Assert
        assertThrows(AuthorizationException.class, () -> userService.updateUserProfile(1L, new UserProfileUpdateDTO(), session));
        verify(userRepository, never()).findById(anyLong());
    }

    @Test
    void testUpdateUserProfile_NotFound() {
        // Arrange
        when(session.getAttribute("userId")).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.updateUserProfile(1L, new UserProfileUpdateDTO(), session));
        verify(userRepository, times(1)).findById(1L);
    }

}
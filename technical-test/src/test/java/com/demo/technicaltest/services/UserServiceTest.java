package com.demo.technicaltest.services;

import com.demo.technicaltest.dto.RegisterDto;
import com.demo.technicaltest.entity.UserEntity;
import com.demo.technicaltest.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_shouldCreateUser_whenInputIsValid() {
        RegisterDto dto = new RegisterDto();
        dto.setUsername("javier");
        dto.setPassword("password123");

        when(userRepository.findByUsername("javier")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");

        UserEntity expectedUser = new UserEntity();
        expectedUser.setUsername("javier");
        expectedUser.setPassword("encodedPassword");

        when(userRepository.save(any(UserEntity.class))).thenReturn(expectedUser);

        UserEntity result = userService.registerUser(dto);

        assertEquals("javier", result.getUsername());
        assertEquals("encodedPassword", result.getPassword());
        verify(userRepository).save(any(UserEntity.class));
    }


    @Test
    void registerUser_shouldThrow_whenUsernameIsEmpty() {
        RegisterDto dto = new RegisterDto();
        dto.setUsername(" ");
        dto.setPassword("password");

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(dto);
        });

        assertEquals("El nombre de usuario no puede estar vacío", e.getMessage());
    }


    @Test
    void registerUser_shouldThrow_whenPasswordIsTooShort() {
        RegisterDto dto = new RegisterDto();
        dto.setUsername("javier");
        dto.setPassword("123");

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(dto);
        });

        assertEquals("La contraseña debe tener al menos 6 caracteres", e.getMessage());
    }


    @Test
    void registerUser_shouldThrow_whenUsernameAlreadyExists() {
        RegisterDto dto = new RegisterDto();
        dto.setUsername("javier");
        dto.setPassword("password123");

        when(userRepository.findByUsername("javier"))
                .thenReturn(Optional.of(new UserEntity()));

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(dto);
        });

        assertEquals("El usuario ya existe", e.getMessage());
    }
}

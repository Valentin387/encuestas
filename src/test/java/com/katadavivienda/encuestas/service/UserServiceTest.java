package com.katadavivienda.encuestas.service;

import com.katadavivienda.encuestas.data.dto.UserDto;
import com.katadavivienda.encuestas.data.entity.User;
import com.katadavivienda.encuestas.data.repository.UserRepository;
import com.katadavivienda.encuestas.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService userService;

    private UserDto userDto;
    private User user;

    @BeforeEach
    void setUp() {
        userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setFirstname("John");
        userDto.setLastname("Doe");
        userDto.setPassword("password123");
        userDto.setCompany("TestCorp");

        user = new User();
        user.setId("657894c24ff8123456789012");
        user.setEmail("test@example.com");
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setPassword("encodedPassword");
        user.setCompany("TestCorp");
    }

    @Test
    void register_success() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtUtil.generateToken("657894c24ff8123456789012", "test@example.com")).thenReturn("jwtToken");

        String token = userService.register(userDto);

        assertEquals("jwtToken", token);
        verify(userRepository).findByEmail("test@example.com");
        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(any(User.class));
        verify(jwtUtil).generateToken("657894c24ff8123456789012", "test@example.com");
    }
}
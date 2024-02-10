package com.example.auth.service;

import com.example.auth.entity.User;
import com.example.auth.entity.UserRegisterDTO;
import com.example.auth.exceptions.UserExistingWithMail;
import com.example.auth.exceptions.UserExistingWithName;
import com.example.auth.fasada.AuthController;
import com.example.auth.repository.UserRepository;
import com.example.auth.services.EmailService;
import com.example.auth.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private EmailService emailService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;
    private UserRegisterDTO userRegisterDTO;

    @BeforeEach
    public void init() {
        userRegisterDTO = UserRegisterDTO.builder()
                .login("test1234")
                .email("test1234@wp.pl")
                .password("12345678").build();
    }
    @Test
    public void UserService_Register_Success() throws UserExistingWithName, UserExistingWithMail {
        when(userRepository.findUserByLogin(Mockito.any(String.class))).thenReturn(Optional.empty());
        when(userRepository.findUserByEmail(Mockito.any(String.class))).thenReturn(Optional.empty());
        when(passwordEncoder.encode(Mockito.any(String.class))).thenReturn("encodedPassword");
        userService.register(userRegisterDTO);
        verify(userRepository, times(1)).saveAndFlush(any(User.class));
        verify(emailService, times(1)).sendActivation(any(User.class));
    }
    @Test
    public void UserService_Register_ThrowsUserWithExistingName() {
        when(userRepository.findUserByLogin(anyString())).thenReturn(Optional.of(new User()));
        assertThrows(UserExistingWithName.class, () -> userService.register(userRegisterDTO));
    }

    @Test
    public void UserService_Register_ThrowsUserWithExistingEmail() {
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(new User()));
        assertThrows(UserExistingWithMail.class, () -> userService.register(userRegisterDTO));
    }

}

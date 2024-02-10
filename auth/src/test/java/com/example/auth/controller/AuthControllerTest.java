package com.example.auth.controller;


import com.example.auth.entity.UserRegisterDTO;
import com.example.auth.exceptions.UserExistingWithName;
import com.example.auth.fasada.AuthController;
import com.example.auth.fasada.UserController;
import com.example.auth.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;
    private UserRegisterDTO userRegisterDTO;
    @BeforeEach
    public void init() {
        userRegisterDTO = UserRegisterDTO.builder()
                .login("test1234")
                .email("test1234@wp.pl")
                .password("12345678").build();
    }

    @Test
    public void AuthController_Register_ReturnSuccess() throws Exception{
        doAnswer(invocation -> null).when(userService).register(any(UserRegisterDTO.class));
        ResultActions response = mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRegisterDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("SUCCESS"));
    }


    @Test
    public void AuthController_Register_ReturnUserExistWithName() throws Exception{
        doThrow(new UserExistingWithName("User already exists with this name")).when(userService).register(any(UserRegisterDTO.class));
        ResultActions response = mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRegisterDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("USER_EXIST_WITH_NAME"));
    }
}

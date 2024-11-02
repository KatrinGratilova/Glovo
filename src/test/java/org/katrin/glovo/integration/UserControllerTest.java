package org.katrin.glovo.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.katrin.glovo.dto.UserCreateDto;
import org.katrin.glovo.entity.Role;
import org.katrin.glovo.exception.UserMessages;
import org.katrin.glovo.repository.User.UserRepository;
import org.katrin.glovo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private final ObjectMapper mapper = new ObjectMapper();
    private UserCreateDto userCreateDto1;

    @BeforeEach
    public void init() {
        userRepository.deleteAll();
        userCreateDto1 = UserCreateDto.builder()
                .email("gr.katrin.05@gmail.com")
                .name("Anna-Maria")
                .phoneNumber("+380632546866")
                .password("Password1!") // Valid password
                .roles(Set.of(Role.ROLE_USER.toString()))
                .build();
    }

    @Test
    public void save_valid() throws Exception {
        mockMvc.perform(post("/users") // Замените на ваш URL для создания пользователя
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userCreateDto1)))
                .andExpect(status().isOk());
    }

    @Test
    public void save_invalidPassword_tooShort() throws Exception {
        userCreateDto1.setPassword("123"); // Invalid: too short
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userCreateDto1)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains(UserMessages.INVALID_PASSWORD_LENGTH.getMessage())));
    }

    @Test
    public void save_invalidPassword_tooLong() throws Exception {
        userCreateDto1.setPassword("VeryLongPassword123456"); // Invalid: too long
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userCreateDto1)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains(UserMessages.INVALID_PASSWORD_LENGTH.getMessage())));
    }

    @Test
    public void save_invalidPassword_noDigits() throws Exception {
        userCreateDto1.setPassword("Password!"); // Invalid: no uppercase letter
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userCreateDto1)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains(UserMessages.INVALID_PASSWORD.getMessage())));
    }

    @Test
    public void save_invalidPassword_noLetters() throws Exception {
        userCreateDto1.setPassword("123456!"); // Invalid: no uppercase letter
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userCreateDto1)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains(UserMessages.INVALID_PASSWORD.getMessage())));
    }

    @Test
    public void save_invalidPassword_noSpecialCharacter() throws Exception {
        userCreateDto1.setPassword("Password1"); // Invalid: no special character
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userCreateDto1)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains(UserMessages.INVALID_PASSWORD.getMessage())));
    }

    @Test
    public void save_invalidPassword_noUppercase() throws Exception {
        userCreateDto1.setPassword("password1!"); // Invalid: no special character
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userCreateDto1)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains(UserMessages.INVALID_PASSWORD.getMessage())));
    }


    @Test
    public void save_invalidPhoneNumber_noPlus() throws Exception {
        userCreateDto1.setPhoneNumber("380632546866"); // Invalid: no plus
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userCreateDto1)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains(UserMessages.INVALID_PHONE_NUMBER.getMessage())));
    }

    @Test
    public void save_invalidPhoneNumber_containsLetters() throws Exception {
        userCreateDto1.setPhoneNumber("+38063254abc"); // Invalid: contains letters
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userCreateDto1)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains(UserMessages.INVALID_PHONE_NUMBER.getMessage())));
    }

    @Test
    public void save_invalidPhoneNumber_containsSymbols() throws Exception {
        userCreateDto1.setPhoneNumber("+38063254689!"); // Invalid: contains letters
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userCreateDto1)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains(UserMessages.INVALID_PHONE_NUMBER.getMessage())));
    }

    @Test
    public void save_invalidPhoneNumber_tooShort() throws Exception {
        userCreateDto1.setPhoneNumber("+38063"); // Invalid: contains letters
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userCreateDto1)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains(UserMessages.INVALID_PHONE_NUMBER.getMessage())));
    }

    @Test
    public void save_invalidPhoneNumber_tooLong() throws Exception {
        userCreateDto1.setPhoneNumber("+3806325468900000"); // Invalid: contains letters
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userCreateDto1)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains(UserMessages.INVALID_PHONE_NUMBER.getMessage())));
    }


    @Test
    public void save_validName_withoutHyphen() throws Exception {
        userCreateDto1.setName("Anna Maria"); // Invalid: contains digits
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userCreateDto1)))
                .andExpect(status().isOk());
    }

    @Test
    public void save_invalidName_withDigits() throws Exception {
        userCreateDto1.setName("Anna1-Maria"); // Invalid: contains digits
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userCreateDto1)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains(UserMessages.INVALID_NAME.getMessage())));
    }

    @Test
    public void save_invalidName_withSymbols() throws Exception {
        userCreateDto1.setName("Anna-Maria!"); // Invalid: contains digits
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userCreateDto1)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains(UserMessages.INVALID_NAME.getMessage())));
    }


    @Test
    public void save_invalidEmail_noAtSymbol() throws Exception {
        userCreateDto1.setEmail("gr.katrin.05gmail.com"); // Invalid: no '@'
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userCreateDto1)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains(UserMessages.INVALID_EMAIL.getMessage())));
    }

    @Test
    public void save_invalidEmail_noDomain() throws Exception {
        userCreateDto1.setEmail("gr.katrin.05gmail.com"); // Invalid: no '@'
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userCreateDto1)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains(UserMessages.INVALID_EMAIL.getMessage())));
    }

    @Test
    public void save_invalidEmail_noCharactersBeforeAt() throws Exception {
        userCreateDto1.setEmail("gr.katrin.05gmail.com"); // Invalid: no '@'
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userCreateDto1)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains(UserMessages.INVALID_EMAIL.getMessage())));
    }
}

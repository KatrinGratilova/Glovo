package org.katrin.glovo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.katrin.glovo.converter.UserCreateConverter;
import org.katrin.glovo.dto.UserCreateDto;
import org.katrin.glovo.entity.Role;
import org.katrin.glovo.entity.UserEntity;
import org.katrin.glovo.exception.UserMessages;
import org.katrin.glovo.repository.User.UserRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;
    private UserCreateDto userCreateDto;

    @BeforeEach
    void setUp() {
        userCreateDto = UserCreateDto.builder()
                //.email("gr.katrin.05@gmail.com")
                .name("Anna-Maria")
                .phoneNumber("+380632546866")
                .password("PassworD12!#") // Valid password
                .roles(Set.of(Role.ROLE_USER.toString()))
                .build();
    }

    @Test
    public void save_ok_nameWithoutHyphen() {
        userCreateDto.setName("Anna");
        UserEntity userEntity = UserCreateConverter.toEntity(userCreateDto);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        UserCreateDto actual = userService.save(userCreateDto);
        assertEquals(actual, userCreateDto);
    }

    @Test
    public void save_ok_nameWithHyphen() {
        UserEntity userEntity = UserCreateConverter.toEntity(userCreateDto);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        UserCreateDto actual = userService.save(userCreateDto);
        assertEquals(actual, userCreateDto);
    }

    @Test
    public void save_invalidName_withDigits() {
        userCreateDto.setName("Anna1-Maria2");
        when(userRepository.save(any(UserEntity.class))).thenThrow(new IllegalArgumentException(UserMessages.INVALID_NAME.getMessage()));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.save(userCreateDto));
        assertEquals(UserMessages.INVALID_NAME.getMessage(), exception.getMessage());
    }

    @Test
    public void save_invalidName_withSymbols() {
        userCreateDto.setName("Anna@-Maria!");
        when(userRepository.save(any(UserEntity.class))).thenThrow(new IllegalArgumentException(UserMessages.INVALID_NAME.getMessage()));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.save(userCreateDto));
        assertEquals(UserMessages.INVALID_NAME.getMessage(), exception.getMessage());
    }

    @Test
    public void save_invalidName_withSpaces() {
        userCreateDto.setName("Anna  Maria");
        when(userRepository.save(any(UserEntity.class))).thenThrow(new IllegalArgumentException(UserMessages.INVALID_NAME.getMessage()));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.save(userCreateDto));
        assertEquals(UserMessages.INVALID_NAME.getMessage(), exception.getMessage());
    }

    @Test
    public void save_invalidName_tooShort() {
        userCreateDto.setName("An");
        when(userRepository.save(any(UserEntity.class))).thenThrow(new IllegalArgumentException(UserMessages.INVALID_NAME_LENGTH.getMessage()));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.save(userCreateDto));
        assertEquals(UserMessages.INVALID_NAME_LENGTH.getMessage(), exception.getMessage());
    }

    @Test
    public void save_invalidName_withCyrillic() {
        userCreateDto.setName("Катеryna");
        when(userRepository.save(any(UserEntity.class))).thenThrow(new IllegalArgumentException(UserMessages.INVALID_NAME.getMessage()));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.save(userCreateDto));
        assertEquals(UserMessages.INVALID_NAME.getMessage(), exception.getMessage());
    }

    @Test
    public void save_invalidPassword_noUppercase() {
        userCreateDto.setPassword("password12#!");
        when(userRepository.save(any(UserEntity.class))).thenThrow(new IllegalArgumentException(UserMessages.INVALID_PASSWORD.getMessage()));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.save(userCreateDto));
        assertEquals(UserMessages.INVALID_PASSWORD.getMessage(), exception.getMessage());
    }

    @Test
    public void save_invalidPassword_noLowerCase() {
        userCreateDto.setPassword("PASSWORD12#!");
        when(userRepository.save(any(UserEntity.class))).thenThrow(new IllegalArgumentException(UserMessages.INVALID_PASSWORD.getMessage()));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.save(userCreateDto));
        assertEquals(UserMessages.INVALID_PASSWORD.getMessage(), exception.getMessage());
    }

    @Test
    public void save_invalidPassword_noDigits() {
        userCreateDto.setPassword("PassworD#!");
        when(userRepository.save(any(UserEntity.class))).thenThrow(new IllegalArgumentException(UserMessages.INVALID_PASSWORD.getMessage()));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.save(userCreateDto));
        assertEquals(UserMessages.INVALID_PASSWORD.getMessage(), exception.getMessage());
    }

    @Test
    public void save_invalidPassword_noSpecialCharacter() {
        userCreateDto.setPassword("PassworD12");
        when(userRepository.save(any(UserEntity.class))).thenThrow(new IllegalArgumentException(UserMessages.INVALID_PASSWORD.getMessage()));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.save(userCreateDto));
        assertEquals(UserMessages.INVALID_PASSWORD.getMessage(), exception.getMessage());
    }

    @Test
    public void save_invalidPassword_tooShort() {
        userCreateDto.setPassword("Ab!2");
        when(userRepository.save(any(UserEntity.class))).thenThrow(new IllegalArgumentException(UserMessages.INVALID_PASSWORD_LENGTH.getMessage()));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.save(userCreateDto));
        assertEquals(UserMessages.INVALID_PASSWORD_LENGTH.getMessage(), exception.getMessage());
    }

    @Test
    public void save_invalidPassword_tooLong() {
        userCreateDto.setPassword("VeryLongPassword123456#!");
        when(userRepository.save(any(UserEntity.class))).thenThrow(new IllegalArgumentException(UserMessages.INVALID_PASSWORD_LENGTH.getMessage()));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.save(userCreateDto));
        assertEquals(UserMessages.INVALID_PASSWORD_LENGTH.getMessage(), exception.getMessage());
    }

    @Test
    public void save_invalidPhoneNumber_noPlus() {
        userCreateDto.setPhoneNumber("380632546866");
        when(userRepository.save(any(UserEntity.class))).thenThrow(new IllegalArgumentException(UserMessages.INVALID_PHONE_NUMBER.getMessage()));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.save(userCreateDto));
        assertEquals(UserMessages.INVALID_PHONE_NUMBER.getMessage(), exception.getMessage());
    }

    @Test
    public void save_invalidPhoneNumber_tooShort() {
        userCreateDto.setPhoneNumber("+38063254");
        when(userRepository.save(any(UserEntity.class))).thenThrow(new IllegalArgumentException(UserMessages.INVALID_PHONE_NUMBER.getMessage()));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.save(userCreateDto));
        assertEquals(UserMessages.INVALID_PHONE_NUMBER.getMessage(), exception.getMessage());
    }

    @Test
    public void save_invalidPhoneNumber_tooLong() {
        userCreateDto.setPhoneNumber("+380632546870000");
        when(userRepository.save(any(UserEntity.class))).thenThrow(new IllegalArgumentException(UserMessages.INVALID_PHONE_NUMBER.getMessage()));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.save(userCreateDto));
        assertEquals(UserMessages.INVALID_PHONE_NUMBER.getMessage(), exception.getMessage());
    }

    @Test
    public void save_invalidPhoneNumber_containsLetters() {
        userCreateDto.setPhoneNumber("+38063254err");
        when(userRepository.save(any(UserEntity.class))).thenThrow(new IllegalArgumentException(UserMessages.INVALID_PHONE_NUMBER.getMessage()));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.save(userCreateDto));
        assertEquals(UserMessages.INVALID_PHONE_NUMBER.getMessage(), exception.getMessage());
    }

    @Test
    public void save_invalidPhoneNumber_containsSymbols() {
        userCreateDto.setPhoneNumber("+38063254686@!");
        when(userRepository.save(any(UserEntity.class))).thenThrow(new IllegalArgumentException(UserMessages.INVALID_PHONE_NUMBER.getMessage()));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.save(userCreateDto));
        assertEquals(UserMessages.INVALID_PHONE_NUMBER.getMessage(), exception.getMessage());
    }

    @Test
    public void save_invalidPhoneNumber_containsSpaces() {
        userCreateDto.setPhoneNumber("+3806325468  9");
        when(userRepository.save(any(UserEntity.class))).thenThrow(new IllegalArgumentException(UserMessages.INVALID_PHONE_NUMBER.getMessage()));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.save(userCreateDto));
        assertEquals(UserMessages.INVALID_PHONE_NUMBER.getMessage(), exception.getMessage());
    }


    

//    // Email Tests
//    @Test
//    public void save_invalidEmail_noAtSymbol() {
//        userCreateDto.setEmail("gr.katrin.05gmail.com"); // Invalid: no '@'
//        when(userRepository.save(any(UserEntity.class))).thenThrow(new IllegalArgumentException(UserMessages.INVALID_EMAIL.getMessage()));
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.save(userCreateDto));
//        assertEquals(UserMessages.INVALID_EMAIL.getMessage(), exception.getMessage());
//    }
//
//    @Test
//    public void save_invalidEmail_noDomain() {
//        userCreateDto.setEmail("gr.katrin.05@"); // Invalid: no domain after '@'
//        when(userRepository.save(any(UserEntity.class))).thenThrow(new IllegalArgumentException(UserMessages.INVALID_EMAIL.getMessage()));
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.save(userCreateDto));
//        assertEquals(UserMessages.INVALID_EMAIL.getMessage(), exception.getMessage());
//    }
//
//    @Test
//    public void save_invalidEmail_noCharactersBeforeAt() {
//        userCreateDto.setEmail("@gmail.com"); // Invalid: no characters before '@'
//        when(userRepository.save(any(UserEntity.class))).thenThrow(new IllegalArgumentException(UserMessages.INVALID_EMAIL.getMessage()));
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.save(userCreateDto));
//        assertEquals(UserMessages.INVALID_EMAIL.getMessage(), exception.getMessage());
//    }
}
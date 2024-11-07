package org.katrin.glovo.service;

import lombok.AllArgsConstructor;
import org.katrin.glovo.converter.OrderConverter;
import org.katrin.glovo.converter.UserConverter;
import org.katrin.glovo.converter.UserCreateConverter;
import org.katrin.glovo.dto.OrderDto;
import org.katrin.glovo.dto.UserCreateDto;
import org.katrin.glovo.dto.UserDto;
import org.katrin.glovo.entity.Role;
import org.katrin.glovo.entity.UserEntity;
import org.katrin.glovo.repository.User.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@AllArgsConstructor

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserCreateDto save(UserCreateDto userDto) {
        UserEntity userEntity = UserCreateConverter.toEntity(userDto);
//        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRoles(new HashSet<>());
        userEntity.getRoles().add(Role.ROLE_USER);
        UserEntity saved = userRepository.save(userEntity);

        return UserCreateConverter.toDto(saved);
    }

    public List<UserDto> getAll() {
        return userRepository.findAll().stream().map(UserConverter::toDto).toList();
    }

    public UserDto getById(int id) {
        return userRepository.findById(id).map(UserConverter::toDto).orElseThrow();
    }



    public UserCreateDto updateWithoutOrders(UserCreateDto userDto) {
        UserEntity userEntity = userRepository.updateWithoutOrders(UserCreateConverter.toEntity(userDto));
        return UserCreateConverter.toDto(userEntity);
    }

    public UserDto addOrder(int userId, OrderDto orderDto) {
        UserEntity userEntity = userRepository.addOrder(userId, OrderConverter.toEntity(orderDto));
        return UserConverter.toDto(userEntity);
    }

    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    public UserCreateDto assignAdminRole(int id) {
        UserEntity user = userRepository.findById(id).orElseThrow();
        user.getRoles().add(Role.ROLE_ADMIN);
        return UserCreateConverter.toDto(userRepository.save(user));
    }
}

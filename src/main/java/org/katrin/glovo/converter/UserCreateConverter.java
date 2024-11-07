package org.katrin.glovo.converter;

import org.katrin.glovo.dto.UserCreateDto;
import org.katrin.glovo.entity.Role;
import org.katrin.glovo.entity.UserEntity;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class UserCreateConverter {
    public static UserEntity toEntity(UserCreateDto user) {
        Set<String> roles = Optional.ofNullable(user.getRoles()).orElse(new HashSet<>());
        return UserEntity.builder()
                .id(user.getId())
                //.email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .name(user.getName())
                .password(user.getPassword())
                .roles(roles.stream().map(Role::valueOf).collect(Collectors.toSet()))
                .build();
    }

    public static UserCreateDto toDto(UserEntity user) {
        Set<Role> roles = Optional.ofNullable(user.getRoles()).orElse(new HashSet<>());
        return UserCreateDto.builder()
                .id(user.getId())
                //.email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .name(user.getName())
                .password(user.getPassword())
                .roles(roles.stream().map(Role::toString).collect(Collectors.toSet()))
                .build();
    }
}

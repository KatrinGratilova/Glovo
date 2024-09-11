package org.katrin.glovo.service;

import lombok.AllArgsConstructor;
import org.katrin.glovo.converter.OrderConverter;
import org.katrin.glovo.converter.UserConverter;
import org.katrin.glovo.dto.OrderDto;
import org.katrin.glovo.dto.UserDto;
import org.katrin.glovo.entity.UserEntity;
import org.katrin.glovo.repository.User.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor

@Service
public class UserService {
    private final UserRepository userRepository;

    public List<UserDto> getAll(){
        return userRepository.findAll().stream().map(UserConverter::toDto).toList();
    }

    public UserDto getById(int id){
        return userRepository.findById(id).map(UserConverter::toDto).orElseThrow();
    }

    public UserDto save(UserDto userDto){
        UserEntity userEntity = userRepository.save(UserConverter.toEntity(userDto));
        return UserConverter.toDto(userEntity);
    }

    public UserDto addOrder(int userId, OrderDto orderDto){
        UserEntity userEntity = userRepository.addOrder(userId, OrderConverter.toEntity(orderDto));
        return UserConverter.toDto(userEntity);
    }

    public void delete(int id){
        userRepository.deleteById(id);
    }
}

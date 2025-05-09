package org.example.autoriaclone.mapper;

import org.example.autoriaclone.dto.UserDto;
import org.example.autoriaclone.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto toDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .phone(user.getPhone())
                .premium(user.getPremium())
                .enabled(user.isEnabled())
                .role(user.getRole())
                .cars(user.getCars())
                .build();
    }
    public User toEntity(UserDto userDto){
        return new User(userDto.getId(), userDto.getUsername(), userDto.getPassword(), userDto.getEmail(), userDto.getPhone(), userDto.isPremium(), userDto.isEnabled(), userDto.getRole(),userDto.getCars());
    }
}

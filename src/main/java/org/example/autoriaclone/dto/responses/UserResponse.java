package org.example.autoriaclone.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.autoriaclone.dto.UserDto;

@Data
@AllArgsConstructor
public class UserResponse {
    public final UserDto user;
    public final String error;
}

package org.example.autoriaclone.dto.requests;

import lombok.Data;

@Data
public class SignInRequest {
    private String username;
    private String password;
}

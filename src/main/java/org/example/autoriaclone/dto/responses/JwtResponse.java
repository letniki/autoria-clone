package org.example.autoriaclone.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    public final String token;
    public final String refresh;
    public final String error;
}

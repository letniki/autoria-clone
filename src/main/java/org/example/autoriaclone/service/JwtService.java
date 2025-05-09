package org.example.autoriaclone.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;

public interface JwtService {
    String extractUsername(String token);
    String generateAccessToken(UserDetails userDetails);
    String generateRefreshToken(UserDetails userDetails);
    boolean isTokenExpired(String token);

    Duration getDuration(String token);
    boolean isRefreshType(String token);
    String getTokenFromAuth(String auth);
}

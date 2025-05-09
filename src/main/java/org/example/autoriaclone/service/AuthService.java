package org.example.autoriaclone.service;

import lombok.Data;
import org.example.autoriaclone.dto.requests.RefreshRequest;
import org.example.autoriaclone.dto.requests.SignInRequest;
import org.example.autoriaclone.dto.responses.JwtResponse;
import org.example.autoriaclone.entity.User;
import org.example.autoriaclone.exceptions.UserBanedException;
import org.example.autoriaclone.mapper.CarMapper;
import org.example.autoriaclone.mapper.UserMapper;
import org.example.autoriaclone.repository.UserRepository;
import org.example.autoriaclone.service.entityServices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Data
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CarMapper carMapper;
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public JwtResponse login(SignInRequest signInRequest){
        try {
            Authentication auth = UsernamePasswordAuthenticationToken
                    .unauthenticated(signInRequest.getUsername(), signInRequest.getPassword());
            authenticationManager.authenticate(auth);
        } catch (AuthenticationException e){
            return new JwtResponse(null,null, e.getMessage());
        }
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(signInRequest.getUsername());
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username);
        try{
            if (!user.isEnabled()){
                throw new UserBanedException("Your account is banned");
            }
        }catch (UserBanedException e){
            return new JwtResponse(null,null,e.getMessage());
        }
        String access = jwtService.generateAccessToken(userDetails);
        String refresh = jwtService.generateRefreshToken(userDetails);
        return new JwtResponse(access, refresh, null);
    }
    public JwtResponse refresh(RefreshRequest refreshRequest){
        String refresh = refreshRequest.getRefresh();
        if (jwtService.isTokenExpired(refresh)){
            return new JwtResponse(null, null, "refresh token expired");
        }
        String username = jwtService.extractUsername(refresh);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        String access = jwtService.generateAccessToken(userDetails);
        if (jwtService.getDuration(refresh).toHours()<12){
            String newRefresh = jwtService.generateRefreshToken(userDetails);
            return new JwtResponse(access, newRefresh, null);
        }
        return new JwtResponse(access, refresh, null);
    }
}

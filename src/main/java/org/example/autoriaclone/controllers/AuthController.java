package org.example.autoriaclone.controllers;

import lombok.RequiredArgsConstructor;
import org.example.autoriaclone.dto.requests.RefreshRequest;
import org.example.autoriaclone.dto.requests.SignInRequest;
import org.example.autoriaclone.dto.responses.JwtResponse;
import org.example.autoriaclone.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> signIn(@RequestBody SignInRequest signInRequest){
        return ResponseEntity.ok(authService.login(signInRequest));
    }
    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(@RequestBody RefreshRequest refreshRequest){
        return ResponseEntity.ok(authService.refresh(refreshRequest));
    }
    }

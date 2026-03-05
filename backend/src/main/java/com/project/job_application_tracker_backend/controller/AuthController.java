package com.project.job_application_tracker_backend.controller;

import com.project.job_application_tracker_backend.dto.AuthResponseDto;
import com.project.job_application_tracker_backend.dto.LoginRequestDto;
import com.project.job_application_tracker_backend.dto.RegisterRequestDto;
import com.project.job_application_tracker_backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register
            (@RequestBody RegisterRequestDto requestDto){

        return ResponseEntity.ok(authService.register(requestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login
            (@RequestBody LoginRequestDto loginRequestDto){

        return ResponseEntity.ok(authService.login(loginRequestDto));
    }
}

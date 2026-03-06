package com.project.job_application_tracker_backend.controller;

import com.project.job_application_tracker_backend.dto.ApiResponseDto;
import com.project.job_application_tracker_backend.dto.AuthResponseDto;
import com.project.job_application_tracker_backend.dto.LoginRequestDto;
import com.project.job_application_tracker_backend.dto.RegisterRequestDto;
import com.project.job_application_tracker_backend.service.AuthService;
import jakarta.validation.Valid;
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
    public ResponseEntity<ApiResponseDto<AuthResponseDto>>register
            (@Valid @RequestBody RegisterRequestDto requestDto){

        AuthResponseDto responseDto = authService.register(requestDto);

        ApiResponseDto<AuthResponseDto> response =
                new ApiResponseDto<>(true, "User registered successfully", responseDto);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto<AuthResponseDto>> login
            (@Valid @RequestBody LoginRequestDto requestDto){

        AuthResponseDto responseDto = authService.login(requestDto);

        ApiResponseDto<AuthResponseDto> response =
                new ApiResponseDto<>(true, "User registered successfully", responseDto);

        return ResponseEntity.ok(response);
    }
}

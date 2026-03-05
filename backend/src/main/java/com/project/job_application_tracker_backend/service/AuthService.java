package com.project.job_application_tracker_backend.service;

import com.project.job_application_tracker_backend.dto.AuthResponseDto;
import com.project.job_application_tracker_backend.dto.LoginRequestDto;
import com.project.job_application_tracker_backend.dto.RegisterRequestDto;

public interface AuthService {

    AuthResponseDto register(RegisterRequestDto requestDto);

    AuthResponseDto login(LoginRequestDto loginRequestDto);

}

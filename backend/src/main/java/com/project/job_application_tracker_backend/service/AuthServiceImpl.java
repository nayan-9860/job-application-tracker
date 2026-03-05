package com.project.job_application_tracker_backend.service;

import com.project.job_application_tracker_backend.dto.AuthResponseDto;
import com.project.job_application_tracker_backend.dto.LoginRequestDto;
import com.project.job_application_tracker_backend.dto.RegisterRequestDto;
import com.project.job_application_tracker_backend.entity.User;
import com.project.job_application_tracker_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public AuthResponseDto register(RegisterRequestDto requestDto) {

//        User user = new User();
//        user.setName(requestDto.getName());
//        user.setEmail(requestDto.getEmail());
//        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        User user = User.builder()
                .name(requestDto.getName())
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        return new AuthResponseDto("dummy-token" , "user registered successfully");
        //TODO - LATER ADD JWT TOKEN
    }

    @Override
    public AuthResponseDto login(LoginRequestDto loginRequestDto) {

        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid password");
        }

        return new AuthResponseDto("dummy-token", "Login successful");
    }
}

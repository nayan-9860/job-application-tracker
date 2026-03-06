package com.project.job_application_tracker_backend.config;

import com.project.job_application_tracker_backend.entity.User;
import com.project.job_application_tracker_backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initAdmin() {

        return args -> {

            if (userRepository.findByEmail("admin@test.com").isEmpty()) {

                User admin = User.builder()
                        .name("Admin")
                        .email("admin@test.com")
                        .password(passwordEncoder.encode("admin123"))
                        .role("ROLE_ADMIN")
                        .build();

                userRepository.save(admin);
            }
        };
    }
}
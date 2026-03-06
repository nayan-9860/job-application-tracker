package com.project.job_application_tracker_backend.controller;

import com.project.job_application_tracker_backend.dto.ApiResponseDto;
import com.project.job_application_tracker_backend.dto.JobApplicationResponseDto;
import com.project.job_application_tracker_backend.service.JobApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final JobApplicationService applicationService;

    // ADMIN can see all jobs
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/jobs")
    public ResponseEntity<ApiResponseDto<List<JobApplicationResponseDto>>>getAllJobs() {

        List<JobApplicationResponseDto> jobs = applicationService.getAllApplications();

        ApiResponseDto<List<JobApplicationResponseDto>> response =
                new ApiResponseDto<>(true, "All job applications fetched successfully", jobs);

        return ResponseEntity.ok(response);
    }
}

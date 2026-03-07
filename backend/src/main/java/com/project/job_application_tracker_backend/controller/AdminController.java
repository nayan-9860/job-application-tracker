package com.project.job_application_tracker_backend.controller;

import com.project.job_application_tracker_backend.dto.ApiResponseDto;
import com.project.job_application_tracker_backend.dto.JobApplicationResponseDto;
import com.project.job_application_tracker_backend.service.JobApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<ApiResponseDto<Page<JobApplicationResponseDto>>>getAllJobs(
            @RequestParam (defaultValue = "0") int page ,
            @RequestParam (defaultValue = "5") int pageSize ,
            @RequestParam(defaultValue = "appliedDate") String sortBy ,
            @RequestParam (defaultValue = "desc") String sortDir
    ) {
        Page<JobApplicationResponseDto> jobs =
                applicationService.getAllApplications(page, pageSize, sortBy, sortDir);

        ApiResponseDto<Page<JobApplicationResponseDto>> response =
                new ApiResponseDto<>(true, "All job applications fetched successfully", jobs);

        return ResponseEntity.ok(response);
    }
}

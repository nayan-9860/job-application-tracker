package com.project.job_application_tracker_backend.controller;

import com.project.job_application_tracker_backend.dto.ApiResponseDto;
import com.project.job_application_tracker_backend.dto.JobApplicationRequestDto;
import com.project.job_application_tracker_backend.dto.JobApplicationResponseDto;
import com.project.job_application_tracker_backend.service.JobApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    // USER can create job
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<ApiResponseDto<JobApplicationResponseDto>> createJobApplication(@Valid @RequestBody
                                                                              JobApplicationRequestDto requestDto){

        JobApplicationResponseDto job = jobApplicationService.createApplication(requestDto);

        ApiResponseDto<JobApplicationResponseDto> response =
                new ApiResponseDto<>(true , "job created successfully" , job);

        return ResponseEntity.ok(response);
    }


    //user can see their all applied  jobs only
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my-jobs")
    public ResponseEntity<ApiResponseDto<Page<JobApplicationResponseDto>>> getMyJobs(
            @RequestParam (defaultValue = "0") int page ,
            @RequestParam (defaultValue = "5") int pageSize ,
            @RequestParam (defaultValue = "appliedDate") String sortBy ,
            @RequestParam (defaultValue = "desc") String sortDir ,
            @RequestParam (required = false) String status ,
            @RequestParam (required = false) String companyName
    ){

        Page<JobApplicationResponseDto> jobs =
                jobApplicationService.getMyApplications(page , pageSize , sortBy ,sortDir ,status , companyName);

        ApiResponseDto<Page<JobApplicationResponseDto>> response =
                new ApiResponseDto<>(true, "Jobs fetched successfully", jobs);

        return ResponseEntity.ok(response);
    }


    // USER can see  their job only
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/detail/{id}")
    public ResponseEntity<ApiResponseDto<JobApplicationResponseDto>>getApplicationById(@PathVariable Long id){

        JobApplicationResponseDto job =
                jobApplicationService.getApplicationById(id);

        ApiResponseDto<JobApplicationResponseDto> response =
                new ApiResponseDto<>(true, "Job fetched successfully", job);

        return ResponseEntity.ok(response);
    }

    //user can update  their jobs only
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<JobApplicationResponseDto>>updateApplication(@PathVariable Long id ,
                                                                      @RequestBody JobApplicationRequestDto requestDto){

        JobApplicationResponseDto job =
                jobApplicationService.updateApplication(id, requestDto);

        ApiResponseDto<JobApplicationResponseDto> response =
                new ApiResponseDto<>(true, "Job updated successfully", job);

        return ResponseEntity.ok(response);
    }

    // USER can delete their job
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<String>> deleteApplication(@PathVariable Long id) {

        jobApplicationService.deleteApplication(id);

        ApiResponseDto<String> response =
                new ApiResponseDto<>(true, "Application deleted successfully", null);

        return ResponseEntity.ok(response);
    }

}

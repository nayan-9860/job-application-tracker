package com.project.job_application_tracker_backend.service;

import com.project.job_application_tracker_backend.dto.JobApplicationRequestDto;
import com.project.job_application_tracker_backend.dto.JobApplicationResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface JobApplicationService {

    JobApplicationResponseDto createApplication(JobApplicationRequestDto dto);

    Page<JobApplicationResponseDto> getMyApplications(
            int page,
            int pageSize,
            String sortBy,
            String sortDir,
            String status,
            String companyName
    );

    JobApplicationResponseDto getApplicationById(Long id);

    JobApplicationResponseDto updateApplication(Long id, JobApplicationRequestDto dto);

    void deleteApplication(Long id);

    Page<JobApplicationResponseDto> getAllApplications(
            int page,
            int size,
            String sortBy,
            String sortDir
    ); // ADMIN
}

package com.project.job_application_tracker_backend.service;

import com.project.job_application_tracker_backend.dto.JobApplicationRequestDto;
import com.project.job_application_tracker_backend.dto.JobApplicationResponseDto;

import java.util.List;

public interface JobApplicationService {

    JobApplicationResponseDto createApplication(JobApplicationRequestDto dto);

    List<JobApplicationResponseDto> getMyApplications();

    JobApplicationResponseDto getApplicationById(Long id);

    JobApplicationResponseDto updateApplication(Long id, JobApplicationRequestDto dto);

    void deleteApplication(Long id);

    List<JobApplicationResponseDto> getAllApplications(); // ADMIN
}

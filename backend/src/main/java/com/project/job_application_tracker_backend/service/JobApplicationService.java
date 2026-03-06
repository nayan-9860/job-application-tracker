package com.project.job_application_tracker_backend.service;

import com.project.job_application_tracker_backend.dto.JobApplicationRequestDto;
import com.project.job_application_tracker_backend.dto.JobApplicationResponseDto;
import com.project.job_application_tracker_backend.entity.JobApplication;

import java.util.List;

public interface JobApplicationService {

    JobApplicationResponseDto createJobApplication(JobApplicationRequestDto requestDto , Long userId);

    List<JobApplicationResponseDto> getAllJobApplicationsByUser(Long userId);

    JobApplicationResponseDto getApplicationById(Long id);

    JobApplicationResponseDto updateJobApllication(Long id , JobApplicationRequestDto requestDto);

    void deleteApplication(Long id);
}

package com.project.job_application_tracker_backend.service;

import com.project.job_application_tracker_backend.dto.JobApplicationRequestDto;
import com.project.job_application_tracker_backend.dto.JobApplicationResponseDto;
import com.project.job_application_tracker_backend.entity.JobApplication;
import com.project.job_application_tracker_backend.entity.JobStatus;
import com.project.job_application_tracker_backend.entity.User;
import com.project.job_application_tracker_backend.exceptions.ResourceNotFoundException;
import com.project.job_application_tracker_backend.repository.JobApplicationRepository;
import com.project.job_application_tracker_backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    // CREATE JOB APPLICATION
    @Override
    public JobApplicationResponseDto createApplication(JobApplicationRequestDto dto) {

        User user = getCurrentUser();

        JobApplication jobApplication = modelMapper.map(dto, JobApplication.class);

        jobApplication.setUser(user);

        JobApplication savedJob = jobApplicationRepository.save(jobApplication);

        return modelMapper.map(savedJob, JobApplicationResponseDto.class);
    }

    // GET CURRENT USER JOBS
    @Override
    public List<JobApplicationResponseDto> getMyApplications() {

        User user = getCurrentUser();

        List<JobApplication> jobs = jobApplicationRepository.findByUserId(user.getId());

        return jobs.stream()
                .map(job -> modelMapper.map(job, JobApplicationResponseDto.class))
                .toList();
    }

    // GET JOB BY ID
    @Override
    public JobApplicationResponseDto getApplicationById(Long id) {

        User user = getCurrentUser();

        JobApplication job = jobApplicationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));

        if (!job.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not allowed to access this job");
        }

        return modelMapper.map(job, JobApplicationResponseDto.class);
    }

    // UPDATE JOB
    @Override
    public JobApplicationResponseDto updateApplication(Long id, JobApplicationRequestDto dto) {

        User user = getCurrentUser();

        JobApplication job = jobApplicationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));

        if (!job.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You cannot update this job");
        }

        job.setCompanyName(dto.getCompanyName());
        job.setJobRole(dto.getJobRole());
        job.setStatus(JobStatus.valueOf(dto.getStatus()));

        JobApplication updatedJob = jobApplicationRepository.save(job);

        return modelMapper.map(updatedJob, JobApplicationResponseDto.class);
    }

    // DELETE JOB
    @Override
    public void deleteApplication(Long id) {

        User user = getCurrentUser();

        JobApplication job = jobApplicationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));

        if (!job.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You cannot delete this job");
        }

        jobApplicationRepository.delete(job);
    }

    // ADMIN API
    @Override
    public List<JobApplicationResponseDto> getAllApplications() {

        List<JobApplication> jobs = jobApplicationRepository.findAll();

        return jobs.stream()
                .map(job -> modelMapper.map(job, JobApplicationResponseDto.class))
                .toList();
    }

    // GET LOGGED-IN USER
    private User getCurrentUser() {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        String email = authentication.getName();

        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
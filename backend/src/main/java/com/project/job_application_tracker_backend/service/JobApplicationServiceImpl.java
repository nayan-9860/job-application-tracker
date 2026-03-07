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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Page<JobApplicationResponseDto> getMyApplications(
            int page,
            int pageSize,
            String sortBy,
            String sortDir,
            String status,
            String companyName
    ) {

        // Create Sort object using normal if-else
        Sort sort;
        if ("asc".equalsIgnoreCase(sortDir)) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }

        Pageable pageable = PageRequest.of(page, pageSize, sort);

        // Get current user email
        User user = getCurrentUser();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Page<JobApplication> applications;

        // Filtering logic
        if (status != null && companyName != null) {
            // Filter by both status and company
            applications = jobApplicationRepository
                    .findByUserEmailAndStatusAndCompanyNameContainingIgnoreCase(email, status, companyName, pageable);
        } else if (status != null) {
            applications = jobApplicationRepository
                    .findByUserEmailAndStatus(email, status, pageable);
        } else if (companyName != null) {
            applications = jobApplicationRepository
                    .findByUserEmailAndCompanyNameContainingIgnoreCase(email, companyName, pageable);
        } else {
            applications = jobApplicationRepository
                    .findByUserEmail(email, pageable);
        }

        // Map to DTO
        return applications.map(app -> modelMapper.map(app, JobApplicationResponseDto.class));
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
    public Page<JobApplicationResponseDto> getAllApplications(
            int page,
            int pageSize,
            String sortBy,
            String sortDir) {

        Pageable pageable = PageRequest.of(
                page,
                pageSize,
                Sort.by(Sort.Direction.fromString(sortDir), sortBy)
        );

        Page<JobApplication> jobs = jobApplicationRepository.findAll(pageable);

        return jobs.map(job -> modelMapper.map(job, JobApplicationResponseDto.class));
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
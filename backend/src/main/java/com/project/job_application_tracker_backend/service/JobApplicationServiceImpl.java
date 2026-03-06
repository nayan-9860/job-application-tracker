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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobApplicationServiceImpl implements JobApplicationService{

    private final JobApplicationRepository applicationRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Override
    public JobApplicationResponseDto createJobApplication(JobApplicationRequestDto requestDto , Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        JobApplication jobApplication = modelMapper.map(requestDto , JobApplication.class);

        jobApplication.setUser(user);

        JobApplication saved = applicationRepository.save(jobApplication);

        return modelMapper.map(saved , JobApplicationResponseDto.class);
    }

    @Override
    public List<JobApplicationResponseDto> getAllJobApplicationsByUser(Long userId) {
        return applicationRepository.findByUserId(userId)
                .stream()
                .map(app -> modelMapper.map(app , JobApplicationResponseDto.class))
                .toList();
    }

    @Override
    public JobApplicationResponseDto getApplicationById(Long id) {
          JobApplication jobApplication = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("job application is not found"));

          return modelMapper.map(jobApplication , JobApplicationResponseDto.class);
    }

    @Override
    public JobApplicationResponseDto updateJobApllication(Long id, JobApplicationRequestDto requestDto) {

        JobApplication application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job application not found with id: \" + id"));

        application.setCompanyName(requestDto.getCompanyName());
        application.setJobRole(requestDto.getJobRole());
        application.setStatus(JobStatus.valueOf(requestDto.getStatus()));

        JobApplication updated = applicationRepository.save(application);

        return modelMapper.map(updated , JobApplicationResponseDto.class);
    }

    @Override
    public void deleteApplication(Long id) {

        JobApplication application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

        applicationRepository.delete(application);
    }

}

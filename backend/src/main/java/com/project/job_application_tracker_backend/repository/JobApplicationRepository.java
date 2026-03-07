package com.project.job_application_tracker_backend.repository;

import com.project.job_application_tracker_backend.entity.JobApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobApplicationRepository extends JpaRepository<JobApplication , Long> {

    //Optional<JobApplication> findByCompanyNameAndRole(String companyName, String role);

    List<JobApplication> findByUserId(Long userId);

    Page<JobApplication> findByUserEmailAndStatus(String email, String status, Pageable pageable);

    Page<JobApplication> findByUserEmailAndCompanyNameContainingIgnoreCase(String email, String companyName, Pageable pageable);

    Page<JobApplication> findByUserEmail(String email, Pageable pageable);

    Page<JobApplication> findByUserEmailAndStatusAndCompanyNameContainingIgnoreCase(String email, String status, String companyName, Pageable pageable);

}

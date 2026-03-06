package com.project.job_application_tracker_backend.repository;

import com.project.job_application_tracker_backend.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobApplicationRepository extends JpaRepository<JobApplication , Long> {

    //Optional<JobApplication> findByCompanyNameAndRole(String companyName, String role);

    List<JobApplication> findByUserId(Long userId);

}

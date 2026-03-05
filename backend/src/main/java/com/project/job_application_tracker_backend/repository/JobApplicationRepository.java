package com.project.job_application_tracker_backend.repository;

import com.project.job_application_tracker_backend.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobApplicationRepository extends JpaRepository<JobApplication , Long> {

}

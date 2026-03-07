package com.project.job_application_tracker_backend.repository;

import com.project.job_application_tracker_backend.entity.JobApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JobApplicationRepository extends JpaRepository<JobApplication , Long> {

    //Optional<JobApplication> findByCompanyNameAndRole(String companyName, String role);

    List<JobApplication> findByUserId(Long userId);

    Page<JobApplication> findByUserEmailAndStatus(String email, String status, Pageable pageable);

    Page<JobApplication> findByUserEmailAndCompanyNameContainingIgnoreCase(String email, String companyName, Pageable pageable);

    Page<JobApplication> findByUserEmail(String email, Pageable pageable);

    Page<JobApplication> findByUserEmailAndStatusAndCompanyNameContainingIgnoreCase(String email, String status, String companyName, Pageable pageable);


    @Query("SELECT COUNT(j) FROM JobApplication j WHERE j.status = 'APPLIED' AND j.user.email = :email")
    Long countAppliedJobs(String email);

    @Query("SELECT COUNT(j) FROM JobApplication j WHERE j.status = 'INTERVIEW' AND j.user.email = :email")
    Long countInterviewJobs(String email);

    @Query("SELECT COUNT(j) FROM JobApplication j WHERE j.status = 'OFFER' AND j.user.email = :email")
    Long countOfferJobs(String email);

    @Query("SELECT COUNT(j) FROM JobApplication j WHERE j.status = 'REJECTED' AND j.user.email = :email")
    Long countRejectedJobs(String email);
}

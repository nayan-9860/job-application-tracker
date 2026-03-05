package com.project.job_application_tracker_backend.repository;

import com.project.job_application_tracker_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User , Long> {

}

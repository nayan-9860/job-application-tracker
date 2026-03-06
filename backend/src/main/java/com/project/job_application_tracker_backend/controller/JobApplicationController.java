package com.project.job_application_tracker_backend.controller;

import com.project.job_application_tracker_backend.dto.JobApplicationRequestDto;
import com.project.job_application_tracker_backend.dto.JobApplicationResponseDto;
import com.project.job_application_tracker_backend.service.JobApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/application")
@RequiredArgsConstructor
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    @PostMapping("/{userId}")
    public ResponseEntity<JobApplicationResponseDto> createJobApplication(@Valid @RequestBody
                                                                              JobApplicationRequestDto requestDto ,
                                                                          @PathVariable Long userId){

        return ResponseEntity.ok(jobApplicationService.createJobApplication(requestDto , userId));

    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<JobApplicationResponseDto>> getAllApplications(@PathVariable Long userId){

        return ResponseEntity.ok(jobApplicationService.getAllJobApplicationsByUser(userId));
    }


    @GetMapping("/detail/{id}")
    public ResponseEntity<JobApplicationResponseDto>getApplicationById(@PathVariable Long id){

        return ResponseEntity.ok(jobApplicationService.getApplicationById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobApplicationResponseDto>updateApplication(@PathVariable Long id ,
                                                                      @RequestBody JobApplicationRequestDto requestDto){

        return ResponseEntity.ok(jobApplicationService.updateJobApllication(id , requestDto ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteApplication(@PathVariable Long id) {

        jobApplicationService.deleteApplication(id);
        return ResponseEntity.ok("Application deleted");
    }

}

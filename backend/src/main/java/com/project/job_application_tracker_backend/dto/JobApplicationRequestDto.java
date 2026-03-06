package com.project.job_application_tracker_backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobApplicationRequestDto {

    @NotBlank(message = "companyName is required")
    private String companyName;

    @NotBlank(message = "job role is required")
    private String jobRole;

    @NotBlank(message = "status is required")
    private String status;

}

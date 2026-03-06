package com.project.job_application_tracker_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JobApplicationResponseDto {

    private Long id;
    private String companyName;
    private String jobRole;
    private String status;

}

package com.project.job_application_tracker_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StatsDto {

    private Long applied;
    private Long interview;
    private Long offer;
    private Long rejected;

}

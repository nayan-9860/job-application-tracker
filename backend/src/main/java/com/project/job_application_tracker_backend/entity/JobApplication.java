package com.project.job_application_tracker_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "job_Applications")
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;

    private String jobRole;

    private String location;

    @Enumerated(EnumType.STRING)
    private JobStatus status;

    private LocalDate applicationDate;

    @Column(length = 1000)
    private String notes;

    private String jobUrl;

    private Date appliedDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_Id")
    private User user;

}

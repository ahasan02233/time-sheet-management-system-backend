package com.ahasan.time_sheet_mngmnt_sys.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "timesheets")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Timesheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String taskDescription;

    private Double hoursWorked;

    private LocalDate workDate;

    // PENDING, APPROVED, REJECTED
    private String status;

    private String managerComment;

    private LocalDateTime submittedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
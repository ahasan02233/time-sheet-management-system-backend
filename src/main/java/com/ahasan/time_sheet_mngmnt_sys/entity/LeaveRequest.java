package com.ahasan.time_sheet_mngmnt_sys.entity;

import com.ahasan.time_sheet_mngmnt_sys.enums.LeaveStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "leave_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String employeeName;

    private String employeeEmail;

    private LocalDate fromDate;

    private LocalDate toDate;

    @Column(length = 1000)
    private String reason;

    @Enumerated(EnumType.STRING)
    private LeaveStatus status;

    @Column(length = 1000)
    private String managerComment;
}

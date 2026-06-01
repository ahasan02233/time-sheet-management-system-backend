package com.ahasan.time_sheet_mngmnt_sys.dtos;

import com.ahasan.time_sheet_mngmnt_sys.enums.LeaveStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaveResponseDto {

    private Long id;

    private String employeeName;

    private String employeeEmail;

    private LocalDate fromDate;

    private LocalDate toDate;

    private long totalDays;

    private String reason;

    private LeaveStatus status;

    private String managerComment;
}

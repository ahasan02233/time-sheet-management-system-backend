package com.ahasan.time_sheet_mngmnt_sys.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetResponseDto {

    private Long id;

    private String employeeName;

    private String taskDescription;

    private Double hoursWorked;

    private LocalDate workDate;

    private String status;

    private String managerComment;
}
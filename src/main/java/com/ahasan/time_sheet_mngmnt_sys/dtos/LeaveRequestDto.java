package com.ahasan.time_sheet_mngmnt_sys.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveRequestDto {

    private String email;

    private LocalDate fromDate;

    private LocalDate toDate;

    private String reason;
}

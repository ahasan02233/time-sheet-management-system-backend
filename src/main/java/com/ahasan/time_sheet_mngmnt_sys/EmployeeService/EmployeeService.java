package com.ahasan.time_sheet_mngmnt_sys.EmployeeService;

import com.ahasan.time_sheet_mngmnt_sys.dtos.*;

import java.util.List;

public interface EmployeeService {

    EmployeeResponseDto registerEmployee(
            EmployeeRequestDto requestDto
    );

    LoginResponseDto loginEmployee(
            LoginRequestDto requestDto
    );

    TimesheetResponseDto createTimesheet(
            TimesheetRequestDto requestDto,
            String employeeEmail
    );

    List<TimesheetResponseDto> getMyTimesheets(
            String employeeEmail
    );

    LeaveResponseDto applyLeave(
            LeaveRequestDto requestDto
    );

    List<LeaveResponseDto> getMyLeaves(
            String email
    );
}
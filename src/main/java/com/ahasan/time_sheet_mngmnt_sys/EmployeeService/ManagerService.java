package com.ahasan.time_sheet_mngmnt_sys.EmployeeService;

import com.ahasan.time_sheet_mngmnt_sys.dtos.LeaveResponseDto;
import com.ahasan.time_sheet_mngmnt_sys.dtos.ManagerActionDto;
import com.ahasan.time_sheet_mngmnt_sys.dtos.TimesheetResponseDto;

import java.util.List;

public interface ManagerService {

    List<TimesheetResponseDto> getPendingTimesheets();

    TimesheetResponseDto approveTimesheet(
            Long timesheetId,
            ManagerActionDto dto
    );

    TimesheetResponseDto rejectTimesheet(
            Long timesheetId,
            ManagerActionDto dto
    );

    List<LeaveResponseDto> getPendingLeaves();

    LeaveResponseDto approveLeave(
            Long leaveId,
            String comment
    );

    LeaveResponseDto rejectLeave(
            Long leaveId,
            String comment
    );
}
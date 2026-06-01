package com.ahasan.time_sheet_mngmnt_sys.EmployeeService;

import com.ahasan.time_sheet_mngmnt_sys.dtos.LeaveResponseDto;
import com.ahasan.time_sheet_mngmnt_sys.dtos.ManagerActionDto;
import com.ahasan.time_sheet_mngmnt_sys.dtos.TimesheetResponseDto;
import com.ahasan.time_sheet_mngmnt_sys.entity.LeaveRequest;
import com.ahasan.time_sheet_mngmnt_sys.entity.Timesheet;
import com.ahasan.time_sheet_mngmnt_sys.enums.LeaveStatus;
import com.ahasan.time_sheet_mngmnt_sys.repos.LeaveRequestRepository;
import com.ahasan.time_sheet_mngmnt_sys.repos.TimesheetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    private final TimesheetRepository timesheetRepository;
    private final LeaveRequestRepository leaveRequestRepository;

    @Override
    public List<TimesheetResponseDto> getPendingTimesheets() {

        return timesheetRepository
                .findByStatus("PENDING")
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TimesheetResponseDto approveTimesheet(
            Long timesheetId,
            ManagerActionDto dto
    ) {

        Timesheet timesheet = timesheetRepository
                .findById(timesheetId)
                .orElseThrow(() ->
                        new RuntimeException("Timesheet not found"));

        timesheet.setStatus("APPROVED");
        timesheet.setManagerComment(dto.getComment());

        Timesheet updated =
                timesheetRepository.save(timesheet);

        return mapToDto(updated);
    }

    @Override
    public TimesheetResponseDto rejectTimesheet(
            Long timesheetId,
            ManagerActionDto dto
    ) {

        Timesheet timesheet = timesheetRepository
                .findById(timesheetId)
                .orElseThrow(() ->
                        new RuntimeException("Timesheet not found"));

        timesheet.setStatus("REJECTED");
        timesheet.setManagerComment(dto.getComment());

        Timesheet updated =
                timesheetRepository.save(timesheet);

        return mapToDto(updated);
    }

    @Override
    public List<LeaveResponseDto> getPendingLeaves() {

        return leaveRequestRepository
                .findByStatus(LeaveStatus.PENDING)
                .stream()
                .map(this::mapLeaveToDto)
                .toList();
    }

    @Override
    public LeaveResponseDto approveLeave(
            Long leaveId,
            String comment
    ) {

        LeaveRequest leave =
                leaveRequestRepository.findById(leaveId)
                        .orElseThrow(() ->
                                new RuntimeException("Leave not found"));

        leave.setStatus(LeaveStatus.APPROVED);
        leave.setManagerComment(comment);

        LeaveRequest updated =
                leaveRequestRepository.save(leave);

        return mapLeaveToDto(updated);
    }

    @Override
    public LeaveResponseDto rejectLeave(
            Long leaveId,
            String comment
    ) {

        LeaveRequest leave =
                leaveRequestRepository.findById(leaveId)
                        .orElseThrow(() ->
                                new RuntimeException("Leave not found"));

        leave.setStatus(LeaveStatus.REJECTED);
        leave.setManagerComment(comment);

        LeaveRequest updated =
                leaveRequestRepository.save(leave);

        return mapLeaveToDto(updated);
    }

    private TimesheetResponseDto mapToDto(
            Timesheet timesheet
    ) {

        TimesheetResponseDto dto =
                new TimesheetResponseDto();

        dto.setId(timesheet.getId());

        dto.setEmployeeName(
                timesheet.getEmployee().getFirstName()
                        + " "
                        + timesheet.getEmployee().getLastName()
        );

        dto.setTaskDescription(
                timesheet.getTaskDescription()
        );

        dto.setHoursWorked(
                timesheet.getHoursWorked()
        );

        dto.setWorkDate(
                timesheet.getWorkDate()
        );

        dto.setStatus(
                timesheet.getStatus()
        );

        dto.setManagerComment(
                timesheet.getManagerComment()
        );

        return dto;
    }

    private LeaveResponseDto mapLeaveToDto(
            LeaveRequest leave
    ) {

        return LeaveResponseDto.builder()
                .id(leave.getId())
                .employeeName(leave.getEmployeeName())
                .employeeEmail(leave.getEmployeeEmail())
                .fromDate(
                        leave.getFromDate()
                )
                .toDate(
                        leave.getToDate()
                )
                .reason(leave.getReason())
                .status(leave.getStatus())
                .managerComment(
                        leave.getManagerComment()
                )
                .build();
    }
}
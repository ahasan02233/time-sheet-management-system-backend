package com.ahasan.time_sheet_mngmnt_sys.EmployeeService;

import com.ahasan.time_sheet_mngmnt_sys.dtos.ManagerActionDto;
import com.ahasan.time_sheet_mngmnt_sys.dtos.TimesheetResponseDto;
import com.ahasan.time_sheet_mngmnt_sys.entity.Timesheet;
import com.ahasan.time_sheet_mngmnt_sys.repos.TimesheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private TimesheetRepository timesheetRepository;

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
                .orElseThrow(() -> new RuntimeException("Timesheet not found"));

        timesheet.setStatus("APPROVED");

        timesheet.setManagerComment(dto.getComment());

        Timesheet updated = timesheetRepository.save(timesheet);

        return mapToDto(updated);
    }

    @Override
    public TimesheetResponseDto rejectTimesheet(
            Long timesheetId,
            ManagerActionDto dto
    ) {

        Timesheet timesheet = timesheetRepository
                .findById(timesheetId)
                .orElseThrow(() -> new RuntimeException("Timesheet not found"));

        timesheet.setStatus("REJECTED");

        timesheet.setManagerComment(dto.getComment());

        Timesheet updated = timesheetRepository.save(timesheet);

        return mapToDto(updated);
    }


    private TimesheetResponseDto mapToDto(Timesheet timesheet) {

        TimesheetResponseDto dto = new TimesheetResponseDto();

        dto.setId(timesheet.getId());
        dto.setEmployeeName(timesheet.getEmployee().getFirstName());
        dto.setEmployeeName(timesheet.getEmployee().getLastName());
        dto.setTaskDescription(timesheet.getTaskDescription());
        dto.setHoursWorked(timesheet.getHoursWorked());
        dto.setWorkDate(timesheet.getWorkDate());
        dto.setStatus(timesheet.getStatus());
        //dto.setManagerComment(timesheet.getManagerComment());

        return dto;
    }
}

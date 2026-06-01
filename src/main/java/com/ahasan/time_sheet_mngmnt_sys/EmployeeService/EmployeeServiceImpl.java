package com.ahasan.time_sheet_mngmnt_sys.EmployeeService;

import com.ahasan.time_sheet_mngmnt_sys.dtos.*;
import com.ahasan.time_sheet_mngmnt_sys.entity.Employee;
import com.ahasan.time_sheet_mngmnt_sys.entity.LeaveRequest;
import com.ahasan.time_sheet_mngmnt_sys.entity.Timesheet;
import com.ahasan.time_sheet_mngmnt_sys.enums.LeaveStatus;
import com.ahasan.time_sheet_mngmnt_sys.repos.EmployeeRepository;
import com.ahasan.time_sheet_mngmnt_sys.repos.LeaveRequestRepository;
import com.ahasan.time_sheet_mngmnt_sys.repos.TimesheetRepository;
import com.ahasan.time_sheet_mngmnt_sys.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final TimesheetRepository timesheetRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public EmployeeResponseDto registerEmployee(
            EmployeeRequestDto requestDto
    ) {

        if (employeeRepository.existsByEmail(
                requestDto.getEmail()
        )) {

            throw new RuntimeException(
                    "Email already exists"
            );
        }

        String encryptedPassword =
                passwordEncoder.encode(
                        requestDto.getPassword()
                );

        Employee employee =
                Employee.builder()
                        .firstName(
                                requestDto.getFirstName()
                        )
                        .lastName(
                                requestDto.getLastName()
                        )
                        .email(
                                requestDto.getEmail()
                        )
                        .password(
                                encryptedPassword
                        )
                        .role(
                                requestDto.getRole()
                        )
                        .createdAt(
                                LocalDateTime.now()
                        )
                        .build();

        Employee savedEmployee =
                employeeRepository.save(employee);

        return EmployeeResponseDto.builder()
                .id(savedEmployee.getId())
                .firstName(savedEmployee.getFirstName())
                .lastName(savedEmployee.getLastName())
                .email(savedEmployee.getEmail())
                .role(savedEmployee.getRole())
                .message("Registered Successfully")
                .build();
    }

    @Override
    public LoginResponseDto loginEmployee(
            LoginRequestDto requestDto
    ) {

        Employee employee =
                employeeRepository.findByEmail(
                        requestDto.getEmail()
                ).orElseThrow(
                        () -> new RuntimeException(
                                "Employee not found"
                        )
                );

        if (!passwordEncoder.matches(
                requestDto.getPassword(),
                employee.getPassword()
        )) {

            throw new RuntimeException(
                    "Invalid Password"
            );
        }

        String token =
                jwtUtil.generateToken(
                        employee.getEmail()
                );

        LoginResponseDto response =
                new LoginResponseDto();

        response.setMessage(
                "Login Successful"
        );

        response.setEmployeeName(
                employee.getFirstName()
                        + " "
                        + employee.getLastName()
        );

        response.setEmail(
                employee.getEmail()
        );

        response.setRole(
                employee.getRole()
        );

        response.setToken(token);

        return response;
    }

    @Override
    public TimesheetResponseDto createTimesheet(
            TimesheetRequestDto requestDto,
            String employeeEmail
    ) {

        Employee employee =
                employeeRepository.findByEmail(
                        employeeEmail
                ).orElseThrow(
                        () -> new RuntimeException(
                                "Employee not found"
                        )
                );

        Timesheet timesheet =
                new Timesheet();

        timesheet.setTaskDescription(
                requestDto.getTaskDescription()
        );

        timesheet.setHoursWorked(
                requestDto.getHoursWorked()
        );

        timesheet.setWorkDate(
                requestDto.getWorkDate()
        );

        timesheet.setStatus(
                "PENDING"
        );

        timesheet.setSubmittedAt(
                LocalDateTime.now()
        );

        timesheet.setEmployee(
                employee
        );

        Timesheet savedTimesheet =
                timesheetRepository.save(
                        timesheet
                );

        return mapTimesheetToDto(
                savedTimesheet
        );
    }

    @Override
    public List<TimesheetResponseDto>
    getMyTimesheets(
            String employeeEmail
    ) {

        Employee employee =
                employeeRepository.findByEmail(
                        employeeEmail
                ).orElseThrow(
                        () -> new RuntimeException(
                                "Employee not found"
                        )
                );

        List<Timesheet> timesheets =
                timesheetRepository.findByEmployeeId(
                        employee.getId()
                );

        return timesheets.stream()
                .map(this::mapTimesheetToDto)
                .collect(Collectors.toList());
    }

    @Override
    public LeaveResponseDto applyLeave(
            LeaveRequestDto requestDto
    ) {

        // Validation
        if (requestDto.getFromDate().isAfter(
                requestDto.getToDate())) {

            throw new RuntimeException(
                    "From Date cannot be after To Date"
            );
        }

        long totalDays =
                ChronoUnit.DAYS.between(
                        requestDto.getFromDate(),
                        requestDto.getToDate()
                ) + 1;

        Employee employee =
                employeeRepository.findByEmail(
                        requestDto.getEmail()
                ).orElseThrow(
                        () -> new RuntimeException(
                                "Employee not found"
                        )
                );

        LeaveRequest leave =
                LeaveRequest.builder()
                        .employeeEmail(
                                employee.getEmail()
                        )
                        .employeeName(
                                employee.getFirstName()
                                        + " "
                                        + employee.getLastName()
                        )
                        .fromDate(
                                requestDto.getFromDate()
                        )
                        .toDate(
                                requestDto.getToDate()
                        )
                        .reason(
                                requestDto.getReason()
                        )
                        .status(
                                LeaveStatus.PENDING
                        )
                        .build();

        LeaveRequest savedLeave =
                leaveRequestRepository.save(leave);

        return mapLeaveToDto(savedLeave);
    }

    @Override
    public List<LeaveResponseDto>
    getMyLeaves(
            String email
    ) {

        return leaveRequestRepository
                .findByEmployeeEmail(email)
                .stream()
                .map(this::mapLeaveToDto)
                .collect(Collectors.toList());
    }

    private TimesheetResponseDto mapTimesheetToDto(
            Timesheet timesheet
    ) {

        TimesheetResponseDto dto =
                new TimesheetResponseDto();

        dto.setId(
                timesheet.getId()
        );

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

    private LeaveResponseDto mapLeaveToDto(LeaveRequest leave) {

        long totalDays = 0;

        if (leave.getFromDate() != null && leave.getToDate() != null) {
            totalDays = ChronoUnit.DAYS.between(
                    leave.getFromDate(),
                    leave.getToDate()
            ) + 1;
        }

        return LeaveResponseDto.builder()
                .id(leave.getId())
                .employeeName(leave.getEmployeeName())
                .employeeEmail(leave.getEmployeeEmail())
                .fromDate(leave.getFromDate())
                .toDate(leave.getToDate())
                .totalDays(totalDays)
                .reason(leave.getReason())
                .status(leave.getStatus())
                .managerComment(leave.getManagerComment())
                .build();
    }
}
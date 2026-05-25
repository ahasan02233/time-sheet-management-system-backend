package com.ahasan.time_sheet_mngmnt_sys.EmployeeService;

import com.ahasan.time_sheet_mngmnt_sys.dtos.EmployeeRequestDto;
import com.ahasan.time_sheet_mngmnt_sys.dtos.EmployeeResponseDto;
import com.ahasan.time_sheet_mngmnt_sys.dtos.LoginRequestDto;
import com.ahasan.time_sheet_mngmnt_sys.dtos.LoginResponseDto;
import com.ahasan.time_sheet_mngmnt_sys.dtos.TimesheetRequestDto;
import com.ahasan.time_sheet_mngmnt_sys.dtos.TimesheetResponseDto;

import com.ahasan.time_sheet_mngmnt_sys.entity.Employee;
import com.ahasan.time_sheet_mngmnt_sys.entity.Timesheet;

import com.ahasan.time_sheet_mngmnt_sys.repos.EmployeeRepository;
import com.ahasan.time_sheet_mngmnt_sys.repos.TimesheetRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final TimesheetRepository timesheetRepository;

    private final PasswordEncoder passwordEncoder;

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

        // ENCRYPT PASSWORD
        String encryptedPassword =
                passwordEncoder.encode(
                        requestDto.getPassword()
                );

        Employee employee = Employee.builder()

                .firstName(requestDto.getFirstName())

                .lastName(requestDto.getLastName())

                .email(requestDto.getEmail())

                .password(encryptedPassword)

                .role(requestDto.getRole())

                .createdAt(LocalDateTime.now())

                .build();

        Employee savedEmployee =
                employeeRepository.save(employee);

        return EmployeeResponseDto.builder()

                .id(savedEmployee.getId())

                .firstName(savedEmployee.getFirstName())

                .lastName(savedEmployee.getLastName())

                .email(savedEmployee.getEmail())

                .role(savedEmployee.getRole())

                .message(
                        "Employee Registered Successfully"
                )

                .build();
    }

    @Override
    public LoginResponseDto loginEmployee(
            LoginRequestDto requestDto
    ) {

        Employee employee = employeeRepository
                .findByEmail(requestDto.getEmail())

                .orElseThrow(() ->
                        new RuntimeException(
                                "Employee not found"
                        ));

        if (!passwordEncoder.matches(
                requestDto.getPassword(),
                employee.getPassword()
        )) {

            throw new RuntimeException(
                    "Invalid Password"
            );
        }

        return new LoginResponseDto(
                "Login Successful",

                employee.getFirstName()
                        + " "
                        + employee.getLastName()
        );
    }

    @Override
    public TimesheetResponseDto createTimesheet(
            TimesheetRequestDto requestDto,
            String employeeEmail
    ) {

        Employee employee = employeeRepository
                .findByEmail(employeeEmail)

                .orElseThrow(() ->
                        new RuntimeException(
                                "Employee not found"
                        ));

        Timesheet timesheet = new Timesheet();

        timesheet.setTaskDescription(
                requestDto.getTaskDescription()
        );

        timesheet.setHoursWorked(
                requestDto.getHoursWorked()
        );

        timesheet.setWorkDate(
                requestDto.getWorkDate()
        );

        timesheet.setStatus("PENDING");

        timesheet.setSubmittedAt(
                LocalDateTime.now()
        );

        timesheet.setEmployee(employee);

        Timesheet savedTimesheet =
                timesheetRepository.save(timesheet);

        return mapToDto(savedTimesheet);
    }

    @Override
    public List<TimesheetResponseDto> getMyTimesheets(
            String employeeEmail
    ) {

        Employee employee = employeeRepository
                .findByEmail(employeeEmail)

                .orElseThrow(() ->
                        new RuntimeException(
                                "Employee not found"
                        ));

        List<Timesheet> timesheets =
                timesheetRepository.findByEmployeeId(
                        employee.getId()
                );

        return timesheets.stream()

                .map(this::mapToDto)

                .collect(Collectors.toList());
    }

    // DTO MAPPING
    private TimesheetResponseDto mapToDto(
            Timesheet timesheet
    ) {

        TimesheetResponseDto dto =
                new TimesheetResponseDto();

        dto.setId(timesheet.getId());

        dto.setEmployeeName(
                timesheet.getEmployee()
                        .getFirstName()
                        + " "
                        + timesheet.getEmployee()
                        .getLastName()
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
}
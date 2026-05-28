package com.ahasan.time_sheet_mngmnt_sys.controller;

import com.ahasan.time_sheet_mngmnt_sys.EmployeeService.EmployeeService;
import com.ahasan.time_sheet_mngmnt_sys.dtos.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@CrossOrigin("*")
@Tag(name = "Employee APIs")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(summary = "Register Employee")
    @PostMapping("/register")
    public ResponseEntity<EmployeeResponseDto> registerEmployee(
            @Valid @RequestBody EmployeeRequestDto requestDto
    ) {

        EmployeeResponseDto response =
                employeeService.registerEmployee(requestDto);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Employee Login")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @RequestBody LoginRequestDto request
    ) {

        LoginResponseDto response =
                employeeService.loginEmployee(request);

        return ResponseEntity.ok(response);
    }
    @Operation(summary = "Create Timesheet")
    @PostMapping("/timesheet/create")
    public ResponseEntity<TimesheetResponseDto> createTimesheet(
            @RequestBody TimesheetRequestDto requestDto
    ) {

        return ResponseEntity.ok(
                employeeService.createTimesheet(
                        requestDto,
                        requestDto.getEmail()
                )
        );
    }

    @Operation(summary = "Get My Timesheets")
    @GetMapping("/timesheet/my")
    public ResponseEntity<List<TimesheetResponseDto>>
    getMyTimesheets(

            @RequestParam String email
    ) {

        return ResponseEntity.ok(
                employeeService.getMyTimesheets(email)
        );
    }
}
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
            @Valid @RequestBody EmployeeRequestDto requestDto) {

        return ResponseEntity.ok(
                employeeService.registerEmployee(requestDto)
        );
    }

    @Operation(summary = "Employee Login")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @RequestBody LoginRequestDto request) {

        return ResponseEntity.ok(
                employeeService.loginEmployee(request)
        );
    }

    @Operation(summary = "Create Timesheet")
    @PostMapping("/timesheet/create")
    public ResponseEntity<TimesheetResponseDto> createTimesheet(
            @RequestBody TimesheetRequestDto requestDto) {

        return ResponseEntity.ok(
                employeeService.createTimesheet(
                        requestDto,
                        requestDto.getEmail()
                )
        );
    }

    @Operation(summary = "Get My Timesheets")
    @GetMapping("/timesheet/my")
    public ResponseEntity<List<TimesheetResponseDto>> getMyTimesheets(
            @RequestParam String email) {

        return ResponseEntity.ok(
                employeeService.getMyTimesheets(email)
        );
    }

    // ==========================
    // LEAVE MANAGEMENT
    // ==========================

    @Operation(summary = "Apply Leave")
    @PostMapping("/leave/apply")
    public ResponseEntity<LeaveResponseDto> applyLeave(
            @RequestBody LeaveRequestDto requestDto) {

        return ResponseEntity.ok(
                employeeService.applyLeave(requestDto)
        );
    }

    @Operation(summary = "Get My Leaves")
    @GetMapping("/leave/my")
    public ResponseEntity<List<LeaveResponseDto>> getMyLeaves(
            @RequestParam String email) {

        return ResponseEntity.ok(
                employeeService.getMyLeaves(email)
        );
    }
}
package com.ahasan.time_sheet_mngmnt_sys.controller;

import com.ahasan.time_sheet_mngmnt_sys.EmployeeService.EmployeeService;
import com.ahasan.time_sheet_mngmnt_sys.dtos.EmployeeRequestDto;
import com.ahasan.time_sheet_mngmnt_sys.dtos.EmployeeResponseDto;
import com.ahasan.time_sheet_mngmnt_sys.dtos.LoginRequestDto;
import com.ahasan.time_sheet_mngmnt_sys.dtos.LoginResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/register")
    public ResponseEntity<EmployeeResponseDto> registerEmployee(
            @Valid @RequestBody EmployeeRequestDto requestDto
    ) {

        EmployeeResponseDto response =
                employeeService.registerEmployee(requestDto);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @RequestBody LoginRequestDto request
    ) {

        LoginResponseDto response =
                employeeService.loginEmployee(request);

        return ResponseEntity.ok(response);
    }
}
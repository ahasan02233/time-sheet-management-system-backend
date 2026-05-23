package com.ahasan.time_sheet_mngmnt_sys.EmployeeService;


import com.ahasan.time_sheet_mngmnt_sys.dtos.EmployeeRequestDto;
import com.ahasan.time_sheet_mngmnt_sys.dtos.EmployeeResponseDto;
import com.ahasan.time_sheet_mngmnt_sys.dtos.LoginRequestDto;
import com.ahasan.time_sheet_mngmnt_sys.dtos.LoginResponseDto;

public interface EmployeeService {

    EmployeeResponseDto registerEmployee(EmployeeRequestDto requestDto);

    //LoginResponseDto login(LoginRequestDto request);

    LoginResponseDto loginEmployee(
            LoginRequestDto requestDto
    );
}

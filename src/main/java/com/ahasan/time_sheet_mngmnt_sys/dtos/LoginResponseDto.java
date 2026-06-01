package com.ahasan.time_sheet_mngmnt_sys.dtos;

import com.ahasan.time_sheet_mngmnt_sys.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {

    private String message;
    private String employeeName;
    private String email;
    private Role role;
    private String token;
}
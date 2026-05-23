package com.ahasan.time_sheet_mngmnt_sys.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeResponseDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String role;

    private String message;
}

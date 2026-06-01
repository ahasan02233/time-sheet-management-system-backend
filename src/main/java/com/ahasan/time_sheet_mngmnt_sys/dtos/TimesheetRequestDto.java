package com.ahasan.time_sheet_mngmnt_sys.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetRequestDto {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Task description is required")
    private String taskDescription;

    @NotNull(message = "Hours worked is required")
    @Positive(message = "Hours worked must be greater than 0")
    private Double hoursWorked;

    @NotNull(message = "Work date is required")
    private LocalDate workDate;
}
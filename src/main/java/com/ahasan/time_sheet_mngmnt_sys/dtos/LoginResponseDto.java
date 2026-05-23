package com.ahasan.time_sheet_mngmnt_sys.dtos;

public class LoginResponseDto {

    private String message;
    private String employeeName;

    public LoginResponseDto() {
    }

    public LoginResponseDto(String message, String employeeName) {
        this.message = message;
        this.employeeName = employeeName;
    }

    public String getMessage() {
        return message;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}

package com.ahasan.time_sheet_mngmnt_sys.dtos;

import com.ahasan.time_sheet_mngmnt_sys.enums.Role;

public class LoginResponseDto {

    private String message;
    private String employeeName;
    private String email;
    private Role role;   // stored as enum
    private String token;

    public LoginResponseDto() {
    }

    public LoginResponseDto(String message, String employeeName,
                            String email, Role role, String token) {
        this.message = message;
        this.employeeName = employeeName;
        this.email = email;
        this.role = role;
        this.token = token;
    }

    // Getters
    public String getMessage() { return message; }
    public String getEmployeeName() { return employeeName; }
    public String getEmail() { return email; }
    public Role getRole() { return role; }
    public String getToken() { return token; }

    // Setters
    public void setMessage(String message) { this.message = message; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }
    public void setEmail(String email) { this.email = email; }
    public void setRole(Role role) { this.role = role; }
    public void setToken(String token) { this.token = token; }
}

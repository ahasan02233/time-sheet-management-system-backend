package com.ahasan.time_sheet_mngmnt_sys.dtos;

import com.ahasan.time_sheet_mngmnt_sys.enums.Role;

public class EmployeeResponseDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;   // ✅ use enum type here
    private String message;

    public EmployeeResponseDto() {}

    public EmployeeResponseDto(Long id, String firstName, String lastName,
                               String email, Role role, String message) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.message = message;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    // Builder
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private Role role;
        private String message;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder firstName(String firstName) { this.firstName = firstName; return this; }
        public Builder lastName(String lastName) { this.lastName = lastName; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder role(Role role) { this.role = role; return this; }
        public Builder message(String message) { this.message = message; return this; }

        public EmployeeResponseDto build() {
            return new EmployeeResponseDto(id, firstName, lastName, email, role, message);
        }
    }
}

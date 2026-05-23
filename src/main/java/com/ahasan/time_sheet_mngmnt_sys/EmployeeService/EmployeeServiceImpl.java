package com.ahasan.time_sheet_mngmnt_sys.EmployeeService;


import com.ahasan.time_sheet_mngmnt_sys.dtos.EmployeeRequestDto;
import com.ahasan.time_sheet_mngmnt_sys.dtos.EmployeeResponseDto;
import com.ahasan.time_sheet_mngmnt_sys.dtos.LoginRequestDto;
import com.ahasan.time_sheet_mngmnt_sys.dtos.LoginResponseDto;
import com.ahasan.time_sheet_mngmnt_sys.entity.Employee;
import com.ahasan.time_sheet_mngmnt_sys.repos.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public EmployeeResponseDto registerEmployee(
            EmployeeRequestDto requestDto
    ) {

        if (employeeRepository.existsByEmail(
                requestDto.getEmail())) {

            throw new RuntimeException(
                    "Email already exists"
            );
        }

        // ENCRYPT PASSWORD
        String encryptedPassword =
                passwordEncoder.encode(
                        requestDto.getPassword()
                );

        System.out.println(
                "Encrypted Password : "
                        + encryptedPassword
        );

        Employee employee = Employee.builder()
                .firstName(requestDto.getFirstName())
                .lastName(requestDto.getLastName())
                .email(requestDto.getEmail())

                // SAVE ENCRYPTED PASSWORD
                .password(encryptedPassword)

                .role("EMPLOYEE")
                .createdAt(LocalDateTime.now())
                .build();

        Employee savedEmployee =
                employeeRepository.save(employee);

        return EmployeeResponseDto.builder()
                .id(savedEmployee.getId())
                .firstName(savedEmployee.getFirstName())
                .lastName(savedEmployee.getLastName())
                .email(savedEmployee.getEmail())
                .role(savedEmployee.getRole())
                .message(
                        "Employee Registered Successfully"
                )
                .build();
    }

    @Override
    public LoginResponseDto loginEmployee(
            LoginRequestDto requestDto
    ) {

        Employee employee = employeeRepository
                .findByEmail(requestDto.getEmail())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Employee not found"
                        ));

        if (!passwordEncoder.matches(
                requestDto.getPassword(),
                employee.getPassword()
        )) {

            throw new RuntimeException(
                    "Invalid Password"
            );
        }

        return new LoginResponseDto(
                "Login Successful",
                employee.getFirstName() + " "
                        + employee.getLastName()
        );
    }
}
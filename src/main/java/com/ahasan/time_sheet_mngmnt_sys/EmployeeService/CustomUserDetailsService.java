package com.ahasan.time_sheet_mngmnt_sys.EmployeeService;

import com.ahasan.time_sheet_mngmnt_sys.entity.Employee;
import com.ahasan.time_sheet_mngmnt_sys.repos.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService
        implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(
            String email
    ) throws UsernameNotFoundException {

        Employee employee = employeeRepository
                .findByEmail(email)

                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found"
                        ));

        return new User(

                employee.getEmail(),

                employee.getPassword(),

                List.of(
                        new SimpleGrantedAuthority(
                                employee.getRole().name()
                        )
                )
        );
    }
}
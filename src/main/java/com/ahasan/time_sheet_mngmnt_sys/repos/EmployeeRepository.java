package com.ahasan.time_sheet_mngmnt_sys.repos;

import com.ahasan.time_sheet_mngmnt_sys.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    //Optional<Employee> findByUsername(String username);


    boolean existsByEmail(String email);
}

package com.ahasan.time_sheet_mngmnt_sys.repos;

import com.ahasan.time_sheet_mngmnt_sys.entity.Timesheet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimesheetRepository
        extends JpaRepository<Timesheet, Long> {

    List<Timesheet> findByEmployeeId(
            Long employeeId
    );

    List<Timesheet> findByStatus(
            String status
    );
}

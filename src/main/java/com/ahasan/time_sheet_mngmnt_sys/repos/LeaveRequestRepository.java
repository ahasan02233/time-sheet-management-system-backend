package com.ahasan.time_sheet_mngmnt_sys.repos;

import com.ahasan.time_sheet_mngmnt_sys.entity.LeaveRequest;
import com.ahasan.time_sheet_mngmnt_sys.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRequestRepository
        extends JpaRepository<LeaveRequest, Long> {

    List<LeaveRequest> findByEmployeeEmail(
            String employeeEmail
    );

    List<LeaveRequest> findByStatus(
            LeaveStatus status
    );
}

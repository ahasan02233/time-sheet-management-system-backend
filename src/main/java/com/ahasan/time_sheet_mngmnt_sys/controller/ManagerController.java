package com.ahasan.time_sheet_mngmnt_sys.controller;

import com.ahasan.time_sheet_mngmnt_sys.EmployeeService.ManagerService;
import com.ahasan.time_sheet_mngmnt_sys.dtos.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ManagerController {

    private final ManagerService managerService;

    // ==========================
    // TIMESHEET MANAGEMENT
    // ==========================

    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @Operation(summary = "Get Pending Timesheets")
    @GetMapping("/pending")
    public ResponseEntity<List<TimesheetResponseDto>> getPendingTimesheets() {

        return ResponseEntity.ok(
                managerService.getPendingTimesheets()
        );
    }

    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @Operation(summary = "Approve Timesheet")
    @PutMapping("/approve/{id}")
    public ResponseEntity<TimesheetResponseDto> approveTimesheet(
            @PathVariable Long id,
            @RequestBody ManagerActionDto dto) {

        return ResponseEntity.ok(
                managerService.approveTimesheet(id, dto)
        );
    }

    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @Operation(summary = "Reject Timesheet")
    @PutMapping("/reject/{id}")
    public ResponseEntity<TimesheetResponseDto> rejectTimesheet(
            @PathVariable Long id,
            @RequestBody ManagerActionDto dto) {

        return ResponseEntity.ok(
                managerService.rejectTimesheet(id, dto)
        );
    }

    // ==========================
    // LEAVE MANAGEMENT
    // ==========================

    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @Operation(summary = "Get Pending Leave Requests")
    @GetMapping("/leave/pending")
    public ResponseEntity<List<LeaveResponseDto>> getPendingLeaves() {

        return ResponseEntity.ok(
                managerService.getPendingLeaves()
        );
    }

    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @Operation(summary = "Approve Leave Request")
    @PutMapping("/leave/{id}/approve")
    public ResponseEntity<LeaveResponseDto> approveLeave(
            @PathVariable Long id,
            @RequestBody LeaveApprovalDto dto) {

        return ResponseEntity.ok(
                managerService.approveLeave(
                        id,
                        dto.getManagerComment()
                )
        );
    }

    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @Operation(summary = "Reject Leave Request")
    @PutMapping("/leave/{id}/reject")
    public ResponseEntity<LeaveResponseDto> rejectLeave(
            @PathVariable Long id,
            @RequestBody LeaveApprovalDto dto) {

        return ResponseEntity.ok(
                managerService.rejectLeave(
                        id,
                        dto.getManagerComment()
                )
        );
    }
}
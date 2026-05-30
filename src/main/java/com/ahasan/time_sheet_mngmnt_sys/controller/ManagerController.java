package com.ahasan.time_sheet_mngmnt_sys.controller;

import com.ahasan.time_sheet_mngmnt_sys.EmployeeService.ManagerService;
import com.ahasan.time_sheet_mngmnt_sys.dtos.ManagerActionDto;
import com.ahasan.time_sheet_mngmnt_sys.dtos.TimesheetResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager")
@CrossOrigin(origins = "http://localhost:5173")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @Operation(summary = "Get Pending Timesheets")
    @GetMapping("/pending")
    public ResponseEntity<List<TimesheetResponseDto>> getPendingTimesheets() {

        return ResponseEntity.ok(
                managerService.getPendingTimesheets()
        );
    }


    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @Operation(summary = "Approve Timesheets with Id")
    @PutMapping("/approve/{id}")
    public ResponseEntity<TimesheetResponseDto> approveTimesheet(
            @PathVariable Long id,
            @RequestBody ManagerActionDto dto
    ) {

        return ResponseEntity.ok(
                managerService.approveTimesheet(id, dto)
        );
    }

    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @Operation(summary = "Reject Timesheets")
    @PutMapping("/reject/{id}")
    public ResponseEntity<TimesheetResponseDto> rejectTimesheet(
            @PathVariable Long id,
            @RequestBody ManagerActionDto dto
    ) {

        return ResponseEntity.ok(
                managerService.rejectTimesheet(id, dto)
        );
    }
}

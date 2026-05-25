package com.ahasan.time_sheet_mngmnt_sys.controller;

import com.ahasan.time_sheet_mngmnt_sys.EmployeeService.ManagerService;
import com.ahasan.time_sheet_mngmnt_sys.dtos.ManagerActionDto;
import com.ahasan.time_sheet_mngmnt_sys.dtos.TimesheetResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @GetMapping("/pending")
    public ResponseEntity<List<TimesheetResponseDto>> getPendingTimesheets() {

        return ResponseEntity.ok(
                managerService.getPendingTimesheets()
        );
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<TimesheetResponseDto> approveTimesheet(
            @PathVariable Long id,
            @RequestBody ManagerActionDto dto
    ) {

        return ResponseEntity.ok(
                managerService.approveTimesheet(id, dto)
        );
    }

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

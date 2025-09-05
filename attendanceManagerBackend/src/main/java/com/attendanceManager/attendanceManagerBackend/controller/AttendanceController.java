package com.attendanceManager.attendanceManagerBackend.controller;

import com.attendanceManager.attendanceManagerBackend.dto.AttendanceDTO;
import com.attendanceManager.attendanceManagerBackend.service.AttendanceService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "http://localhost:4200")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping
    public ResponseEntity<List<AttendanceDTO>> getAllAttendanceRecords() {
        try {
            List<AttendanceDTO> records = attendanceService.getAllAttendanceRecords();
            return ResponseEntity.ok(records);
        } catch(EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Collections.emptyList()); // Return empty list instead of String
        }

    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getAttendanceRecord(@PathVariable String id) {
        try {
            AttendanceDTO attendancerecrord = attendanceService.getAttendanceRecordById(id);
            return ResponseEntity.ok(attendancerecrord);
        }catch(EntityNotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Attendance record not found with id: " + id);
        }
    }


    @PostMapping("/save")
    public ResponseEntity<?> SaveAttendance(@RequestBody AttendanceDTO attendanceDTO) {
        try{
            AttendanceDTO savedRecord = attendanceService.saveAttendance(attendanceDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedRecord);
        }catch (IllegalArgumentException e){
            return ResponseEntity
                    .badRequest()
                    .body("Error saving attendance record: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAttendanceRecord(
            @PathVariable String id,
            @RequestBody AttendanceDTO attendanceDTO) {
        try{
            AttendanceDTO updatedRecord = attendanceService.updateAttendanceRecord(id, attendanceDTO);
            return ResponseEntity.ok(updatedRecord);
        }catch(EntityNotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Attendance record not found with id: " + id);
        }catch (IllegalArgumentException e){
            return ResponseEntity
                    .badRequest()
                    .body("Error updating attendance record: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAttendanceRecord(@PathVariable String id) {
        try{
            attendanceService.deleteAttendanceRecord(id);
            return ResponseEntity.ok("Attendance record deleted successfully with id: " + id);

        }catch(EntityNotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Attendance record not found with id: " + id);
        }
    }
}

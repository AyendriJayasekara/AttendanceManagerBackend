package com.attendanceManager.attendanceManagerBackend.controller;

import com.attendanceManager.attendanceManagerBackend.dto.PersonalInfoDTO;
import com.attendanceManager.attendanceManagerBackend.service.PersonalInfoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/personal-info")
@CrossOrigin(origins = "http://localhost:4200")
public class PersonalInfoController {

    @Autowired
    private PersonalInfoService personalInfoService;

    @GetMapping
    public ResponseEntity<?> getSelfPersonalInfo() {
        try {
            PersonalInfoDTO personalInfo = personalInfoService.getSelfPersonalInfo();
            return ResponseEntity.ok(personalInfo);
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Personal information not found");
        }
    }

    @PostMapping
    public ResponseEntity<?> saveSelfPersonalInfo(@RequestBody PersonalInfoDTO personalInfoDTO) {
        try {
            PersonalInfoDTO savedInfo = personalInfoService.saveSelfPersonalInfo(personalInfoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedInfo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body("Error saving personal information: " + e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> updateSelfPersonalInfo(@RequestBody PersonalInfoDTO personalInfoDTO) {
        try {
            PersonalInfoDTO updatedInfo = personalInfoService.updateSelfPersonalInfo(personalInfoDTO);
            return ResponseEntity.ok(updatedInfo);
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Personal information not found");
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body("Error updating personal information: " + e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deletePersonalInfo() {
        try {
            personalInfoService.deleteSelfPersonalInfo();
            return ResponseEntity.ok("Personal information deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Personal information not found");
        }
    }

    @DeleteMapping("/all")
    public ResponseEntity<?> deleteAllPersonalInfo() {
        personalInfoService.deleteAllPersonalInfo();
        return ResponseEntity.ok("All personal information records deleted");
    }
}

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
    public ResponseEntity<?> getPersonalInfo() {
        try {
            PersonalInfoDTO personalInfo = personalInfoService.getPersonalInfo();
            return ResponseEntity.ok(personalInfo);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No personal information found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching personal information: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createPersonalInfo(@RequestBody PersonalInfoDTO personalInfoDTO) {
        try {
            PersonalInfoDTO savedInfo = personalInfoService.savePersonalInfo(personalInfoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedInfo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating personal information: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePersonalInfo(
            @PathVariable String id,
            @RequestBody PersonalInfoDTO personalInfoDTO) {
        try {
            PersonalInfoDTO updatedInfo = personalInfoService.updatePersonalInfo(id, personalInfoDTO);
            return ResponseEntity.ok(updatedInfo);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Personal information not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating personal information: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePersonalInfo(@PathVariable String id) {
        try {
            personalInfoService.deletePersonalInfo(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Personal information not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting personal information: " + e.getMessage());
        }
    }
}

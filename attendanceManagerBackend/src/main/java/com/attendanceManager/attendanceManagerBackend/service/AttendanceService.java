package com.attendanceManager.attendanceManagerBackend.service;

import com.attendanceManager.attendanceManagerBackend.dto.AttendanceDTO;
import com.attendanceManager.attendanceManagerBackend.model.AttendanceRecord;
import com.attendanceManager.attendanceManagerBackend.repository.AttendanceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    public List<AttendanceDTO> getAllAttendanceRecords() {
        return attendanceRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AttendanceDTO getAttendanceRecordById(String id) {
        AttendanceRecord record = attendanceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Attendance record not found with id: " + id));
        return convertToDTO(record);
    }

    public AttendanceDTO saveAttendance(AttendanceDTO dto) {
        // Create new attendance record for check-in
        AttendanceRecord record = new AttendanceRecord();
        record.setDate(dto.getDate());
        record.setCheckInTime(dto.getCheckInTime());
        record.setCheckOutTime(null); // Initially, check-out time is null
        // Save the record with only check-in information
        AttendanceRecord savedRecord = attendanceRepository.save(record);
        return convertToDTO(savedRecord);
    }



    public AttendanceDTO updateAttendanceRecord(String id, AttendanceDTO dto) {
        AttendanceRecord existingRecord = attendanceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Attendance record not found with id: " + id));

        existingRecord.setDate(dto.getDate());
        existingRecord.setCheckInTime(dto.getCheckInTime());
        existingRecord.setCheckOutTime(dto.getCheckOutTime());

        AttendanceRecord updatedRecord = attendanceRepository.save(existingRecord);
        return convertToDTO(updatedRecord);
    }

    public void deleteAttendanceRecord(String id) {
        if (!attendanceRepository.existsById(id)) {
            throw new EntityNotFoundException("Attendance record not found with id: " + id);
        }
        attendanceRepository.deleteById(id);
    }

    private AttendanceDTO convertToDTO(AttendanceRecord record) {
        AttendanceDTO dto = new AttendanceDTO();
        dto.setId(record.getId());
        dto.setDate(record.getDate());
        dto.setCheckInTime(record.getCheckInTime());
        dto.setCheckOutTime(record.getCheckOutTime());
        return dto;
    }


}

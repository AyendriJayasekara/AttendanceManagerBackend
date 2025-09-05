package com.attendanceManager.attendanceManagerBackend.repository;

import com.attendanceManager.attendanceManagerBackend.model.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<AttendanceRecord, String> {
}

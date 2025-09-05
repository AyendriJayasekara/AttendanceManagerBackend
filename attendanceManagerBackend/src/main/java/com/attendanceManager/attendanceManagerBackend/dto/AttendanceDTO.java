package com.attendanceManager.attendanceManagerBackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class AttendanceDTO {
    private String id;
    @JsonProperty("checkInDate")
    private LocalDate date;
    private String checkInTime;
    private String checkOutTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }


}

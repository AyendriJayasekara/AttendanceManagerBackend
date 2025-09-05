package com.attendanceManager.attendanceManagerBackend.repository;

import com.attendanceManager.attendanceManagerBackend.model.PersonalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonalInfoRepository extends JpaRepository<PersonalInfo, String> {

}

package com.attendanceManager.attendanceManagerBackend.service;

import com.attendanceManager.attendanceManagerBackend.dto.PersonalInfoDTO;
import com.attendanceManager.attendanceManagerBackend.model.PersonalInfo;
import com.attendanceManager.attendanceManagerBackend.repository.PersonalInfoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PersonalInfoService {

    @Autowired
    private PersonalInfoRepository personalInfoRepository;

    public PersonalInfoDTO getPersonalInfo(String employeeId) {
        PersonalInfo info = personalInfoRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Personal information not found for employee ID: " + employeeId));
        return convertToDTO(info);
    }

    public PersonalInfoDTO getSelfPersonalInfo() {
        PersonalInfo info = personalInfoRepository.findTopByOrderByIdAsc()
                .orElseThrow(() -> new EntityNotFoundException("Personal information not found"));
        return convertToDTO(info);
    }

    public PersonalInfoDTO savePersonalInfo(PersonalInfoDTO dto) {
        // Check if employee already exists
        if (personalInfoRepository.findByEmployeeId(dto.getEmployeeId()).isPresent()) {
            throw new IllegalArgumentException("Employee with ID " + dto.getEmployeeId() + " already exists. Use update instead.");
        }
        
        PersonalInfo info = convertToEntity(dto);
        PersonalInfo savedInfo = personalInfoRepository.save(info);
        return convertToDTO(savedInfo);
    }

    public PersonalInfoDTO saveSelfPersonalInfo(PersonalInfoDTO dto) {
        // If a self record already exists, prevent duplicate creation
        if (personalInfoRepository.findTopByOrderByIdAsc().isPresent()) {
            throw new IllegalArgumentException("Personal information already exists. Use update instead.");
        }
        // Ensure a stable employeeId for single-user scenario
        if (dto.getEmployeeId() == null || dto.getEmployeeId().isBlank()) {
            dto.setEmployeeId("SELF");
        }
        PersonalInfo info = convertToEntity(dto);
        PersonalInfo savedInfo = personalInfoRepository.save(info);
        return convertToDTO(savedInfo);
    }

    public PersonalInfoDTO updatePersonalInfo(String employeeId, PersonalInfoDTO dto) {
        PersonalInfo existingInfo = personalInfoRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Personal information not found for employee ID: " + employeeId));

        updatePersonalInfo(existingInfo, dto);

        PersonalInfo updatedInfo = personalInfoRepository.save(existingInfo);
        return convertToDTO(updatedInfo);
    }

    public PersonalInfoDTO updateSelfPersonalInfo(PersonalInfoDTO dto) {
        PersonalInfo existingInfo = personalInfoRepository.findTopByOrderByIdAsc()
                .orElseThrow(() -> new EntityNotFoundException("Personal information not found"));
        // Preserve existing employeeId if not provided
        if (dto.getEmployeeId() == null || dto.getEmployeeId().isBlank()) {
            dto.setEmployeeId(existingInfo.getEmployeeId() != null ? existingInfo.getEmployeeId() : "SELF");
        }
        updatePersonalInfo(existingInfo, dto);
        PersonalInfo updatedInfo = personalInfoRepository.save(existingInfo);
        return convertToDTO(updatedInfo);
    }

    public void deletePersonalInfo(String employeeId) {
        List<PersonalInfo> personalInfos = personalInfoRepository.findAllByEmployeeId(employeeId);
        if (personalInfos.isEmpty()) {
            throw new EntityNotFoundException("Personal information not found for employee ID: " + employeeId);
        }
        personalInfoRepository.deleteAll(personalInfos);
    }

    public void deleteSelfPersonalInfo() {
        PersonalInfo info = personalInfoRepository.findTopByOrderByIdAsc()
                .orElseThrow(() -> new EntityNotFoundException("Personal information not found"));
        personalInfoRepository.delete(info);
    }

    public void deleteAllPersonalInfo() {
        personalInfoRepository.deleteAll();
    }



    private PersonalInfoDTO convertToDTO(PersonalInfo info) {
        PersonalInfoDTO dto = new PersonalInfoDTO();
        dto.setEmployeeId(info.getEmployeeId());
        dto.setFullName(info.getFullName());
        dto.setEmail(info.getEmail());
        dto.setPhone(info.getPhone());
        dto.setDepartment(info.getDepartment());
        dto.setJobTitle(info.getJobTitle());
        dto.setDateOfBirth(info.getDateOfBirth());
        dto.setDateOfJoining(info.getDateOfJoining());
        dto.setManager(info.getManager());
        dto.setEmployeeType(info.getEmployeeType());
        dto.setAddress(info.getAddress());
        return dto;
    }

    private PersonalInfo convertToEntity(PersonalInfoDTO dto) {
        PersonalInfo info = new PersonalInfo();
        info.setEmployeeId(dto.getEmployeeId());
        info.setFullName(dto.getFullName());
        info.setEmail(dto.getEmail());
        info.setPhone(dto.getPhone());
        info.setDepartment(dto.getDepartment());
        info.setJobTitle(dto.getJobTitle());
        info.setDateOfBirth(dto.getDateOfBirth());
        info.setDateOfJoining(dto.getDateOfJoining());
        info.setManager(dto.getManager());
        info.setEmployeeType(dto.getEmployeeType());
        info.setAddress(dto.getAddress());
        return info;
    }

    private void updatePersonalInfo(PersonalInfo info, PersonalInfoDTO dto) {
        info.setFullName(dto.getFullName());
        info.setEmail(dto.getEmail());
        info.setPhone(dto.getPhone());
        info.setDepartment(dto.getDepartment());
        info.setJobTitle(dto.getJobTitle());
        info.setDateOfBirth(dto.getDateOfBirth());
        info.setDateOfJoining(dto.getDateOfJoining());
        info.setManager(dto.getManager());
        info.setEmployeeType(dto.getEmployeeType());
        info.setAddress(dto.getAddress());
    }
}

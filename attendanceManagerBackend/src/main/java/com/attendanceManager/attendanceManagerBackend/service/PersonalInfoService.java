package com.attendanceManager.attendanceManagerBackend.service;

import com.attendanceManager.attendanceManagerBackend.dto.PersonalInfoDTO;
import com.attendanceManager.attendanceManagerBackend.model.PersonalInfo;
import com.attendanceManager.attendanceManagerBackend.repository.PersonalInfoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PersonalInfoService {

    @Autowired
    private PersonalInfoRepository personalInfoRepository;


    public PersonalInfoDTO getPersonalInfo() {
        PersonalInfo info = personalInfoRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new EntityNotFoundException("No personal information found."));
        return convertToDTO(info);
    }



    public PersonalInfoDTO savePersonalInfo(PersonalInfoDTO dto) {
        // Convert DTO to entity inline
        PersonalInfo info = new PersonalInfo();
        info .setFullName(dto.getFullName());
        info.setEmail(dto.getEmail());
        info.setPhone(dto.getPhone());
        info.setDepartment(dto.getDepartment());
        info.setJobTitle(dto.getJobTitle());
        info.setDateOfBirth(dto.getDateOfBirth());
        info.setDateOfJoining(dto.getDateOfJoining());
        info.setManager(dto.getManager());
        info.setEmployeeType(dto.getEmployeeType());
        info.setAddress(dto.getAddress());

        PersonalInfo savedInfo = personalInfoRepository.save(info);
        return convertToDTO(savedInfo);
    }



    public PersonalInfoDTO updatePersonalInfo(String id, PersonalInfoDTO dto) {
        PersonalInfo existingInfo = personalInfoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Personal information not found for ID: " + id));

        // Update entity fields inline
        existingInfo.setFullName(dto.getFullName());
        existingInfo.setEmail(dto.getEmail());
        existingInfo.setPhone(dto.getPhone());
        existingInfo.setDepartment(dto.getDepartment());
        existingInfo.setJobTitle(dto.getJobTitle());
        existingInfo.setDateOfBirth(dto.getDateOfBirth());
        existingInfo.setDateOfJoining(dto.getDateOfJoining());
        existingInfo.setManager(dto.getManager());
        existingInfo.setEmployeeType(dto.getEmployeeType());
        existingInfo.setAddress(dto.getAddress());

        PersonalInfo updatedInfo = personalInfoRepository.save(existingInfo);
        return convertToDTO(updatedInfo);
    }




    public void deletePersonalInfo(String id) {
        if (!personalInfoRepository.existsById(id)) {
            throw new EntityNotFoundException("Personal information not found for ID: " + id);
        }
        personalInfoRepository.deleteById(id);
    }

    private PersonalInfoDTO convertToDTO(PersonalInfo personalInfo) {
        PersonalInfoDTO dto = new PersonalInfoDTO();
        dto.setId(personalInfo.getId());
        dto.setFullName(personalInfo.getFullName());
        dto.setEmail(personalInfo.getEmail());
        dto.setPhone(personalInfo.getPhone());
        dto.setDepartment(personalInfo.getDepartment());
        dto.setJobTitle(personalInfo.getJobTitle());
        dto.setDateOfBirth(personalInfo.getDateOfBirth());
        dto.setDateOfJoining(personalInfo.getDateOfJoining());
        dto.setManager(personalInfo.getManager());
        dto.setEmployeeType(personalInfo.getEmployeeType());
        dto.setAddress(personalInfo.getAddress());
        return dto;
    }

}

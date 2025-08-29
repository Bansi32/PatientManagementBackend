package com.pm.patient_service.service;

import com.pm.patient_service.dto.PatientRequestDTO;
import com.pm.patient_service.dto.PatientResponseDTO;
import com.pm.patient_service.mapper.PatientMapper;
import com.pm.patient_service.model.Patient;
import com.pm.patient_service.repository.PatientRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    private PatientRepo patientRepo;

    public PatientService(PatientRepo patientRepo) {
        this.patientRepo = patientRepo;
    }

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepo.findAll();
        List<PatientResponseDTO> patientResponseDTOList = patients.stream().map(PatientMapper::toPatientDTO).toList();
        return patientResponseDTOList;
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        Patient newPatient = patientRepo.save(PatientMapper.toPatientRequestDTO(patientRequestDTO));
        return PatientMapper.toPatientDTO(newPatient);
    }
}

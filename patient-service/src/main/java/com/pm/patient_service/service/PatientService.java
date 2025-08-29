package com.pm.patient_service.service;

import com.pm.patient_service.dto.PatientRequestDTO;
import com.pm.patient_service.dto.PatientResponseDTO;
import com.pm.patient_service.exception.EmailAlreadyExistsException;
import com.pm.patient_service.exception.PatientNotFoundException;
import com.pm.patient_service.mapper.PatientMapper;
import com.pm.patient_service.model.Patient;
import com.pm.patient_service.repository.PatientRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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
        if(patientRepo.existsByEmail(patientRequestDTO.getEmail())){
            throw new EmailAlreadyExistsException("A patient with this email " + "already exists"+patientRequestDTO.getEmail());
        }
        Patient newPatient = patientRepo.save(PatientMapper.toPatientRequestDTO(patientRequestDTO));
        return PatientMapper.toPatientDTO(newPatient);
    }

    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
        Patient patient=patientRepo.findById(id).orElseThrow(()->new PatientNotFoundException("Patient not found with ID: "+ id));

        if(patientRepo.existsByEmailAndIdNot(patientRequestDTO.getEmail(),id)){
            throw new EmailAlreadyExistsException("A patient with this email " + "already exists"+patientRequestDTO.getEmail());
        }
        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        Patient updatedPatient = patientRepo.save(patient);
        return PatientMapper.toPatientDTO(updatedPatient);
    }

    public void deletePatient(UUID id) {
        patientRepo.deleteById(id);
    }
}

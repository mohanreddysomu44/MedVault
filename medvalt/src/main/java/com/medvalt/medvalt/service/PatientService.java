package com.medvalt.medvalt.service;

import com.medvalt.medvalt.entity.Patient;
import com.medvalt.medvalt.entity.User;
import com.medvalt.medvalt.repository.PatientRepository;
import com.medvalt.medvalt.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    public PatientService(PatientRepository patientRepository, UserRepository userRepository) {
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(Long id) {
        return patientRepository.findById(id).orElse(null);
    }

    public Patient savePatient(Patient patient) {
        if (patient.getUser() != null && patient.getUser().getId() != null) {
            User user = userRepository.findById(patient.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            patient.setUser(user);
        }
        return patientRepository.save(patient);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }
}
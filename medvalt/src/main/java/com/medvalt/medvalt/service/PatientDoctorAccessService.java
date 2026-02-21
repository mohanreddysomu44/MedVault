package com.medvalt.medvalt.service;

import com.medvalt.medvalt.entity.Patient;
import com.medvalt.medvalt.entity.Doctor;
import com.medvalt.medvalt.entity.PatientDoctorAccess;
import com.medvalt.medvalt.repository.PatientDoctorAccessRepository;
import com.medvalt.medvalt.repository.PatientRepository;
import com.medvalt.medvalt.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientDoctorAccessService {

    private final PatientDoctorAccessRepository accessRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public PatientDoctorAccessService(PatientDoctorAccessRepository accessRepository,
                                      PatientRepository patientRepository,
                                      DoctorRepository doctorRepository) {
        this.accessRepository = accessRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    // ✅ Grant access using entity references
    public void grantAccess(Long patientId, Long doctorId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow();
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();

        PatientDoctorAccess access = new PatientDoctorAccess();
        access.setPatient(patient);   // use entity reference
        access.setDoctor(doctor);     // use entity reference
        access.setGranted(true);

        accessRepository.save(access);
    }

    // ✅ Revoke access by checking entity IDs
    public void revokeAccess(Long patientId, Long doctorId) {
        accessRepository.findAll().stream()
                .filter(a -> a.getPatient().getId().equals(patientId)
                        && a.getDoctor().getId().equals(doctorId))
                .forEach(accessRepository::delete);
    }

    // ✅ Get all access records for a patient
    public List<PatientDoctorAccess> getAccessListForPatient(Long patientId) {
        return accessRepository.findAll().stream()
                .filter(a -> a.getPatient().getId().equals(patientId))
                .toList();
    }

    // ✅ Get all access records for a doctor
    public List<PatientDoctorAccess> getAccessListForDoctor(Long doctorId) {
        return accessRepository.findAll().stream()
                .filter(a -> a.getDoctor().getId().equals(doctorId))
                .toList();
    }
}
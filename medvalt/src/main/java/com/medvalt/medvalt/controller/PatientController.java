package com.medvalt.medvalt.controller;

import com.medvalt.medvalt.entity.Patient;
import com.medvalt.medvalt.entity.PatientDoctorAccess;
import com.medvalt.medvalt.service.PatientService;
import com.medvalt.medvalt.service.PatientDoctorAccessService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;
    private final PatientDoctorAccessService accessService;

    public PatientController(PatientService patientService, PatientDoctorAccessService accessService) {
        this.patientService = patientService;
        this.accessService = accessService;
    }

    // 🔹 Get all patients
    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    // 🔹 Get patient by ID
    @GetMapping("/{id}")
    public Patient getPatient(@PathVariable Long id) {
        return patientService.getPatientById(id);
    }

    // 🔹 Create patient
    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) {
        return patientService.savePatient(patient);
    }

    // 🔹 Update patient
    @PutMapping("/{id}")
    public Patient updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
        patient.setId(id);
        return patientService.savePatient(patient);
    }

    // 🔹 Delete patient by ID
    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
    }

    // 🔹 Clear all patients (testing)
    @DeleteMapping("/clear")
    public String clearPatients() {
        patientService.getAllPatients().forEach(p -> patientService.deletePatient(p.getId()));
        return "All patients deleted!";
    }

    // 🔹 Grant doctor access
    @PostMapping("/{patientId}/grantAccess/{doctorId}")
    public String grantAccess(@PathVariable Long patientId, @PathVariable Long doctorId) {
        accessService.grantAccess(patientId, doctorId);
        return "Access granted for doctor " + doctorId;
    }

    // 🔹 Revoke doctor access
    @DeleteMapping("/{patientId}/revokeAccess/{doctorId}")
    public String revokeAccess(@PathVariable Long patientId, @PathVariable Long doctorId) {
        accessService.revokeAccess(patientId, doctorId);
        return "Access revoked for doctor " + doctorId;
    }

    // 🔹 List all doctors who have access to this patient
    @GetMapping("/{patientId}/accessList")
    public List<PatientDoctorAccess> getAccessList(@PathVariable Long patientId) {
        return accessService.getAccessListForPatient(patientId);
    }
}
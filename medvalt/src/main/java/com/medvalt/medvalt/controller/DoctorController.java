package com.medvalt.medvalt.controller;

import com.medvalt.medvalt.entity.Doctor;
import com.medvalt.medvalt.entity.Patient;
import com.medvalt.medvalt.entity.PatientDoctorAccess;
import com.medvalt.medvalt.service.DoctorService;
import com.medvalt.medvalt.service.PatientService;
import com.medvalt.medvalt.service.PatientDoctorAccessService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    private final DoctorService doctorService;
    private final PatientService patientService;
    private final PatientDoctorAccessService accessService;

    public DoctorController(DoctorService doctorService,
                            PatientService patientService,
                            PatientDoctorAccessService accessService) {
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.accessService = accessService;
    }

    // 🔹 Get all doctors
    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    // 🔹 Get doctor by ID
    @GetMapping("/{id}")
    public Doctor getDoctor(@PathVariable Long id) {
        return doctorService.getDoctorById(id);
    }

    // 🔹 Create doctor
    @PostMapping
    public Doctor createDoctor(@RequestBody Doctor doctor) {
        return doctorService.saveDoctor(doctor);
    }

    // 🔹 Update doctor
    @PutMapping("/{id}")
    public Doctor updateDoctor(@PathVariable Long id, @RequestBody Doctor doctor) {
        doctor.setId(id);
        return doctorService.saveDoctor(doctor);
    }

    // 🔹 Delete doctor by ID
    @DeleteMapping("/{id}")
    public void deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
    }

    // 🔹 Clear all doctors (testing)
    @DeleteMapping("/clear")
    public String clearDoctors() {
        doctorService.getAllDoctors().forEach(d -> doctorService.deleteDoctor(d.getId()));
        return "All doctors deleted!";
    }

    // 🔹 Fetch patient records (with permission check)
    @GetMapping("/{doctorId}/patient/{patientId}")
    public Object getPatientRecords(@PathVariable Long doctorId, @PathVariable Long patientId) {
        boolean hasAccess = accessService.getAccessListForDoctor(doctorId).stream()
                .anyMatch(a -> a.getPatient().getId().equals(patientId) && a.isGranted());
        if (!hasAccess) {
            return "Access denied: Patient " + patientId + " has not granted access to Doctor " + doctorId;
        }

        return patientService.getPatientById(patientId);
    }

    // 🔹 List all patients this doctor has access to
    @GetMapping("/{doctorId}/accessList")
    public List<PatientDoctorAccess> getDoctorAccessList(@PathVariable Long doctorId) {
        return accessService.getAccessListForDoctor(doctorId);
    }
}
package com.medvalt.medvalt.controller;

import com.medvalt.medvalt.entity.Appointment;
import com.medvalt.medvalt.service.AppointmentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    // 🧑 Patient books appointment with description & report
    @PostMapping("/book")
    public Appointment bookAppointment(
            @RequestParam Long patientId,
            @RequestParam Long doctorId,
            @RequestParam String description,
            @RequestParam String date,
            @RequestParam String timeSlot,
            @RequestParam(required = false) MultipartFile report
    ) throws IOException {
        return service.bookWithReport(patientId, doctorId, description, date, timeSlot, report);
    }

    // 👨‍⚕️ Doctor approves
    @PutMapping("/{id}/approve")
    public Appointment approve(@PathVariable Long id) {
        return service.approve(id);
    }

    // 👨‍⚕️ Doctor rejects
    @PutMapping("/{id}/reject")
    public Appointment reject(@PathVariable Long id) {
        return service.reject(id);
    }

    // Doctor view appointments
    @GetMapping("/doctor/{doctorId}")
    public List<Appointment> doctorAppointments(@PathVariable Long doctorId) {
        return service.getDoctorAppointments(doctorId);
    }

    // Patient view appointments
    @GetMapping("/patient/{patientId}")
    public List<Appointment> patientAppointments(@PathVariable Long patientId) {
        return service.getPatientAppointments(patientId);
    }
}
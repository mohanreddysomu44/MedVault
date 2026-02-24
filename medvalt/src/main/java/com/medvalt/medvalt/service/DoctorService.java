package com.medvalt.medvalt.service;

import com.medvalt.medvalt.entity.Doctor;
import com.medvalt.medvalt.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id).orElse(null);
    }

    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

    // ✅ FIX for /doctor/user/{userId}
    public Doctor getDoctorByUserId(Long userId) {
        return doctorRepository.findByUserId(userId).orElse(null);
    }
}
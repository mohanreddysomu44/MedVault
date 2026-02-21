package com.medvalt.medvalt.service;

import com.medvalt.medvalt.entity.Doctor;
import com.medvalt.medvalt.entity.User;
import com.medvalt.medvalt.repository.DoctorRepository;
import com.medvalt.medvalt.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;

    public DoctorService(DoctorRepository doctorRepository, UserRepository userRepository) {
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id).orElse(null);
    }

    public Doctor saveDoctor(Doctor doctor) {
        if (doctor.getUser() != null && doctor.getUser().getId() != null) {
            User user = userRepository.findById(doctor.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            doctor.setUser(user);
        }
        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }
}
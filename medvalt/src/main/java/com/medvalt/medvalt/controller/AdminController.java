package com.medvalt.medvalt.controller;

import com.medvalt.medvalt.entity.User;
import com.medvalt.medvalt.repository.UserRepository;
import com.medvalt.medvalt.repository.PatientRepository;
import com.medvalt.medvalt.repository.DoctorRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public AdminController(UserRepository userRepository,
                           PatientRepository patientRepository,
                           DoctorRepository doctorRepository) {
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    // 🔹 List all users
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 🔹 Get user by ID
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // 🔹 Assign role to a user
    @PutMapping("/users/{id}/role")
    public User updateUserRole(@PathVariable Long id, @RequestParam String role) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setRole(role); // e.g., ROLE_PATIENT, ROLE_DOCTOR, ROLE_ADMIN
            return userRepository.save(user);
        }
        return null;
    }

    // 🔹 Delete user by ID
    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "User deleted!";
    }

    // 🔹 Clear all users, patients, and doctors
    @DeleteMapping("/clearAll")
    public String clearAll() {
        patientRepository.deleteAll();
        doctorRepository.deleteAll();
        userRepository.deleteAll();
        return "All users, patients, and doctors deleted!";
    }

    // 🔹 Clear only users
    @DeleteMapping("/clearUsers")
    public String clearUsers() {
        userRepository.deleteAll();
        return "All users deleted!";
    }

    // 🔹 Clear only patients
    @DeleteMapping("/clearPatients")
    public String clearPatients() {
        patientRepository.deleteAll();
        return "All patients deleted!";
    }

    // 🔹 Clear only doctors
    @DeleteMapping("/clearDoctors")
    public String clearDoctors() {
        doctorRepository.deleteAll();
        return "All doctors deleted!";
    }
}
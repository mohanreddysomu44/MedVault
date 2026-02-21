package com.medvalt.medvalt.repository;
import com.medvalt.medvalt.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {}

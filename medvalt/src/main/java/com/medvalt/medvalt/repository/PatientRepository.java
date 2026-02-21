package com.medvalt.medvalt.repository;

import com.medvalt.medvalt.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {}


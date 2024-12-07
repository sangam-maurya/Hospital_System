package com.demo.service;


import com.demo.entity.Patient;
import com.demo.repositery.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    public Patient registerPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient getPatientDetails(Long patientId) {
        return patientRepository.findById(patientId).orElseThrow(() -> new RuntimeException("Patient not found"));
    }
}
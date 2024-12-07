package com.demo.service;

import com.demo.entity.Patient;
import com.demo.repositery.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowUpService {
    @Autowired
    private PatientRepository patientRepository;

    public void sendFollowUpNotification(Long patientId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new RuntimeException("Patient not found"));
        // Send follow-up notification logic (Email/SMS)
    }

    public void recordPatientFeedback(Long patientId, String feedback) {
        // Store patient feedback for quality improvement
    }
}
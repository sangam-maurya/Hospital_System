package com.demo.service;


import com.demo.entity.Physician;
import com.demo.repositery.PhysicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhysicianService {
    @Autowired
    private PhysicianRepository physicianRepository;

    public Physician getPhysicianDetails(Long physicianId) {
        return physicianRepository.findById(physicianId).orElseThrow(() -> new RuntimeException("Physician not found"));
    }

    public Physician addPhysician(Physician physician) {
        return physicianRepository.save(physician);
    }
}

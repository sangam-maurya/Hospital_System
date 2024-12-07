package com.demo.controller;

import com.demo.entity.Physician;
import com.demo.service.PhysicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/physicians")
public class PhysicianController {
    @Autowired
    private PhysicianService physicianService;

    @PostMapping("/add")
    public ResponseEntity<Physician> addPhysician(@RequestBody Physician physician) {
        Physician newPhysician = physicianService.addPhysician(physician);
        return ResponseEntity.ok(newPhysician);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Physician> getPhysician(@PathVariable Long id) {
        Physician physician = physicianService.getPhysicianDetails(id);
        return ResponseEntity.ok(physician);
    }
}
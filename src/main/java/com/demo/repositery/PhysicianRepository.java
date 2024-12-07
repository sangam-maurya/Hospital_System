package com.demo.repositery;

import com.demo.entity.Appointment;
import com.demo.entity.Physician;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhysicianRepository extends JpaRepository<Physician, Long> {

}
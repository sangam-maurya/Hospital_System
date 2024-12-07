package com.demo.repositery;

import com.demo.entity.Appointment;
import com.demo.entity.Physician;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByPhysicianAndAppointmentDateTime(Physician physician, LocalDateTime appointmentDateTime);
    List<Appointment> findByPhysician(Physician physician);
    List<Appointment> findByPhysicianAndAppointmentDateTimeBetween(Physician physician, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
package com.demo.controller;

import com.demo.entity.Appointment;
import com.demo.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    // Book an appointment (POST request)
    @PostMapping("/book")
    public ResponseEntity<String> bookAppointment(@RequestParam Long patientId,
                                                  @RequestParam Long physicianId,
                                                  @RequestBody Appointment appointment) {
        try {
            Appointment bookedAppointment = appointmentService.bookAppointment(patientId, physicianId, appointment);
            return new ResponseEntity<>("Appointment booked successfully. Appointment ID: " + bookedAppointment.getId(), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Get appointment by ID (GET request)
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointment(@PathVariable Long id) {
        try {
            Appointment appointment = appointmentService.getAppointment(id);
            return new ResponseEntity<>(appointment, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to handle reset of appointment slots (Optional - For testing purpose or scheduling)
    // You can trigger this manually or have it run on a schedule.
    @PostMapping("/reset")
    public ResponseEntity<String> resetAppointmentSlots() {
        appointmentService.resetAppointmentSlots();
        return new ResponseEntity<>("Appointment slots reset successfully.", HttpStatus.OK);
    }
}
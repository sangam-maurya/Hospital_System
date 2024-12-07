package com.demo.service;


import com.demo.entity.Appointment;
import com.demo.entity.Patient;
import com.demo.entity.Physician;
import com.demo.repositery.AppointmentRepository;
import com.demo.repositery.PatientRepository;
import com.demo.repositery.PhysicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private PhysicianRepository physicianRepository;

    @Autowired
    private PatientRepository patientRepository;

    private static final List<String> availableSlots = List.of("10:15", "11:15", "12:15", "16:15", "17:15"); // Define available slots

    public Appointment bookAppointment(Long patientId, Long physicianId, Appointment appointment) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        Physician physician = physicianRepository.findById(physicianId)
                .orElseThrow(() -> new RuntimeException("Physician not found"));
        appointment.setPatient(patient);
        appointment.setPhysician(physician);
        LocalDateTime requestedDateTime = appointment.getAppointmentDateTime();
        List<Appointment> existingAppointments = appointmentRepository.findByPhysician(physician);
        boolean isAvailable = true;
        for (Appointment existingAppointment : existingAppointments) {
            LocalDateTime existingTime = existingAppointment.getAppointmentDateTime();
            if (!requestedDateTime.isBefore(existingTime.minusHours(1)) &&
                    !requestedDateTime.isAfter(existingTime.plusHours(1))) {
                isAvailable = false;
                break;
            }
        }

        if (isAvailable) {
            appointment.setConfirmationStatus("Booked");
            return appointmentRepository.save(appointment);
        } else {
            LocalDateTime nextAvailableSlot = findNextAvailableSlot(requestedDateTime, existingAppointments);
            throw new RuntimeException("The selected slot is booked. Next available slot: " + nextAvailableSlot);
        }
    }

    private LocalDateTime findNextAvailableSlot(LocalDateTime requestedDateTime, List<Appointment> existingAppointments) {
        LocalDateTime nextAvailableSlot = null;
        int daysAhead = 0;
        outerLoop:
        while (nextAvailableSlot == null) {
            LocalDateTime dayStart = requestedDateTime.plusDays(daysAhead).toLocalDate().atStartOfDay();

            for (String slot : availableSlots) {
                LocalDateTime potentialSlot = dayStart.plusHours(Integer.parseInt(slot.split(":")[0]))
                        .plusMinutes(Integer.parseInt(slot.split(":")[1]));
                if (potentialSlot.isAfter(requestedDateTime)) {
                    boolean isSlotFree = true;
                    for (Appointment existingAppointment : existingAppointments) {
                        LocalDateTime existingTime = existingAppointment.getAppointmentDateTime();
                        if (!potentialSlot.isBefore(existingTime.minusHours(1)) &&
                                !potentialSlot.isAfter(existingTime.plusHours(1))) {
                            isSlotFree = false;
                            break;
                        }
                    }

                    if (isSlotFree) {
                        nextAvailableSlot = potentialSlot;
                        break outerLoop;
                    }
                }
            }
            daysAhead++;
        }

        return nextAvailableSlot;
    }



    public boolean isSlotAvailable(LocalDateTime dateTime, Physician physician) {
        List<Appointment> appointments = appointmentRepository.findByPhysicianAndAppointmentDateTime(physician, dateTime );
        LocalDateTime blockedStart = LocalDateTime.of(dateTime.toLocalDate(), LocalTime.of(10, 15)); // 10:15 AM
        LocalDateTime blockedEnd = blockedStart.plusHours(1); // 11:15 AM, if the 10:15 slot is taken

        if (appointments.isEmpty()) {
            List<Appointment> blockedAppointments = appointmentRepository.findByPhysicianAndAppointmentDateTime(physician, blockedStart);
            return blockedAppointments.isEmpty();
        }

        return false;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void resetAppointmentSlots() {
        LocalDateTime now = LocalDateTime.now();
        List<Appointment> appointments = appointmentRepository.findAll();
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentDateTime().isBefore(now)) {
                appointment.setConfirmationStatus("Available");
                appointment.setNotes("Slot is available for the next booking.");
                appointmentRepository.save(appointment);
            }
        }
    }

    public LocalDateTime findNextAvailableSlot(Physician physician) {
        LocalDateTime now = LocalDateTime.now();
        int daysAhead = 0;
        while (true) {
            LocalDateTime dayStart = now.plusDays(daysAhead).toLocalDate().atStartOfDay();

            for (String slot : availableSlots) {
                LocalDateTime slotTime = dayStart.plusHours(Integer.parseInt(slot.split(":")[0]))
                        .plusMinutes(Integer.parseInt(slot.split(":")[1]));
                if (slotTime.isAfter(now) && isSlotAvailable(slotTime, physician)) {
                    return slotTime;
                }
            }
            daysAhead++;
        }
    }



    public Appointment getAppointment (Long id){
            return appointmentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Appointment not found"));
        }

}
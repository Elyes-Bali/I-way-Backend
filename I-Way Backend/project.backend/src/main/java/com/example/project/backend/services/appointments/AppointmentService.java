package com.example.project.backend.services.appointments;

import com.example.project.backend.entity.Appointment;

import java.util.List;

public interface AppointmentService {
    void acceptAppointment(Long appointmentId);
    void rejectAppointment(Long appointmentId);
    List<Appointment> getAllAppointments();
}

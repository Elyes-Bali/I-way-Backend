package com.example.project.backend.services.appointments;

import com.example.project.backend.entity.Appointment;
import com.example.project.backend.entity.AppointmentStatus;
import com.example.project.backend.repositories.AppointmentRepository;
import io.jsonwebtoken.io.IOException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class AppointmentServiceImpl implements AppointmentService{
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Override
    public void acceptAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IOException("Appointment not found"));

        appointment.setStatus(AppointmentStatus.ACCEPTED);
        appointmentRepository.save(appointment);

    }

    @Override
    public void rejectAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IOException("Appointment not found"));

        appointment.setStatus(AppointmentStatus.REJECTED);
        appointmentRepository.save(appointment);

    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
}

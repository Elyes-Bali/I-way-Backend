package com.example.project.backend.controllers;

import com.example.project.backend.entity.Appointment;
import com.example.project.backend.entity.AppointmentStatus;
import com.example.project.backend.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/notifications")
@CrossOrigin(origins = "http://localhost:4200")
public class NotificationController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @PutMapping("/appointment/{appointmentId}/status")
    public Map<String, Object> updateAppointmentStatus(
            @PathVariable Long appointmentId,
            @RequestParam String status) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setStatus(AppointmentStatus.valueOf(status));
        appointmentRepository.save(appointment);

        // Prepare notification data
        Map<String, String> notification = new HashMap<>();
        notification.put("message", "Your appointment has been " + status.toLowerCase());
        notification.put("status", status);
        notification.put("appointmentId", appointmentId.toString());

        // Send real-time notification to patient
        messagingTemplate.convertAndSend("/topic/patient-" + appointment.getPatient().getId(), notification);
        System.out.println("Sending notification to: /topic/patient-" + appointment.getPatient().getId());

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Appointment status updated successfully");
        return response;
    }
}

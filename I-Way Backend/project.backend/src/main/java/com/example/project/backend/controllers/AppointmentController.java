package com.example.project.backend.controllers;

import com.example.project.backend.entity.Appointment;
import com.example.project.backend.entity.AppointmentStatus;
import com.example.project.backend.entity.User;
import com.example.project.backend.repositories.AppointmentRepository;
import com.example.project.backend.repositories.UserRepository;
import com.example.project.backend.services.appointments.AppointmentService;
import com.example.project.backend.services.mailing.MailService;
import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/appointments")
@CrossOrigin(origins = "http://localhost:4200")
public class AppointmentController {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MailService mailService;


    @PostMapping("/book")
    public ResponseEntity<Map<String, String>> bookAppointment(@RequestParam Long doctorId, @RequestParam Long patientId, @RequestBody Appointment appointment) {
        User doctor = userRepository.findById(doctorId).orElseThrow(() -> new RuntimeException("Doctor not found"));
        User patient = userRepository.findById(patientId).orElseThrow(() -> new RuntimeException("Patient not found"));

        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointmentRepository.save(appointment);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Appointment booked successfully!");

        return ResponseEntity.ok(response);
    }


    @GetMapping("/doctor/{doctorId}")
    public List<Map<String, Object>> getDoctorAppointments(@PathVariable Long doctorId) {
        List<Appointment> appointments = appointmentRepository.findByDoctorId(doctorId);
        List<Map<String, Object>> response = new ArrayList<>();

        for (Appointment appointment : appointments) {
            Map<String, Object> appointmentDetails = new HashMap<>();
            appointmentDetails.put("id", appointment.getId());
            appointmentDetails.put("date", appointment.getDate());
            appointmentDetails.put("time", appointment.getTime());
            appointmentDetails.put("accepted", appointment.isAccepted());
            appointmentDetails.put("status", appointment.getStatus());
            appointmentDetails.put("fullDate", appointment.getFullDate());
            appointmentDetails.put("canceled",appointment.isCanceled());

            Map<String, String> patientDetails = new HashMap<>();
            if (appointment.getPatient() != null) {
                patientDetails.put("id", String.valueOf(appointment.getPatient().getId()));
                patientDetails.put("name", appointment.getPatient().getName());
                patientDetails.put("email", appointment.getPatient().getEmail());
            }

            appointmentDetails.put("patient", patientDetails);
            response.add(appointmentDetails);
        }

        return response;
    }


    @GetMapping("/patient/{patientId}")
    public List<Map<String, Object>> getPatientAppointments(@PathVariable Long patientId) {
        List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);
        List<Map<String, Object>> response = new ArrayList<>();

        for (Appointment appointment : appointments) {
            Map<String, Object> appointmentDetails = new HashMap<>();
            appointmentDetails.put("id", appointment.getId());
            appointmentDetails.put("date", appointment.getDate());
            appointmentDetails.put("time", appointment.getTime());
            appointmentDetails.put("accepted", appointment.isAccepted());
            appointmentDetails.put("status", appointment.getStatus());
            appointmentDetails.put("fullDate", appointment.getFullDate());
            appointmentDetails.put("canceled",appointment.isCanceled());

            Map<String, String> doctorDetails = new HashMap<>();
            if (appointment.getDoctor() != null) {
                doctorDetails.put("id", String.valueOf(appointment.getDoctor().getId()));
                doctorDetails.put("name", appointment.getDoctor().getName());
                doctorDetails.put("email", appointment.getDoctor().getEmail());
            }

            appointmentDetails.put("doctor", doctorDetails);
            response.add(appointmentDetails);
        }

        return response;
    }

    @PutMapping("/app/{appointmentId}/status")
    public ResponseEntity<Map<String, Object>> updateAppointmentStatus(
            @PathVariable Long appointmentId, @RequestParam AppointmentStatus status) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IOException("Appointment not found"));

        appointment.setStatus(status);
        appointmentRepository.save(appointment);

        String patientEmail = appointment.getPatient().getEmail();
        String patientName = appointment.getPatient().getName();
        String doctorName = appointment.getDoctor().getName();
        mailService.sendAppointmentStatusEmail(patientEmail, patientName,doctorName ,status.name());

        // Prepare the response as a JSON object
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Appointment status updated successfully");
        response.put("updatedStatus", appointment.getStatus().toString());  // Include updated status in response

        Map<String, String> notification = new HashMap<>();
        notification.put("message", "Dr. " + doctorName + " has " + status + " your appointment.");
        notification.put("status", String.valueOf(status));
        notification.put("appointmentId", appointmentId.toString());
        // Send real-time notification to patient
        messagingTemplate.convertAndSend("/topic/patient-" + appointment.getPatient().getId(), notification);
        System.out.println("Sending notification to: /topic/patient-" + appointment.getPatient().getId());

        return ResponseEntity.ok(response);  // Return JSON response
    }

    @GetMapping("/all")
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }


    @PutMapping("/canceled/{appointmentId}/cancel")
    public ResponseEntity<Map<String, String>> cancelAppointment(@PathVariable Long appointmentId) {
        appointmentService.cancelAppointment(appointmentId);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Appointment canceled successfully");

        return ResponseEntity.ok(response);
    }


}

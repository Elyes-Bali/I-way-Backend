package com.example.project.backend.controllers;

import com.example.project.backend.entity.DoctorAvailability;
import com.example.project.backend.entity.User;

import com.example.project.backend.repositories.DoctorAvailabilityRepository;
import com.example.project.backend.repositories.UserRepository;
import com.example.project.backend.services.doctorServ.DoctorService;
import com.example.project.backend.services.reclamationsServ.ReclamationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/availability")
@CrossOrigin(origins = "http://localhost:4200")
public class DoctorAvailabilityController {
    @Autowired
    private DoctorAvailabilityRepository doctorAvailabilityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/set")
    public String setAvailability(@RequestParam Long doctorId, @RequestBody List<DoctorAvailability> availabilities) {
        User doctor = userRepository.findById(doctorId).orElseThrow(() -> new RuntimeException("Doctor not found"));

        for (DoctorAvailability availability : availabilities) {
            availability.setDoctor(doctor);
            doctorAvailabilityRepository.save(availability);
        }

        return "Availability updated successfully!";
    }


    @GetMapping("/get/{doctorId}")
    public List<DoctorAvailability> getAvailability(@PathVariable Long doctorId) {
        return doctorAvailabilityRepository.findByDoctorId(doctorId);
    }

    @GetMapping("/doctors")
    public List<DoctorAvailability> getAllDoctorAvailabilities() {
        return doctorAvailabilityRepository.findAll();
    }

    @DeleteMapping("/deletapp/{id}")
    public  void  deleteAppointment(@PathVariable Long id) {
        doctorService.deleteAppointment(id);
    }

}

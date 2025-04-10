package com.example.project.backend.controllers;
import com.example.project.backend.entity.Appointment;
import com.example.project.backend.entity.MedicalFile;
import com.example.project.backend.entity.Posts;
import com.example.project.backend.entity.User;
import com.example.project.backend.repositories.AppointmentRepository;
import com.example.project.backend.repositories.UserRepository;
import com.example.project.backend.services.medicalFile.MedicalFileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medical")
@CrossOrigin(origins = "http://localhost:4200")
public class MedicalFileController {
    @Autowired
    private MedicalFileServiceImpl medicalFileService;

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/doctor/{doctorId}")
    public List<MedicalFile> getMedicalFilesForDoctor(@PathVariable Long doctorId) {
        return medicalFileService.getMedicalFilesForDoctor(doctorId);
    }

    @GetMapping("/patient/{patientId}")
    public List<MedicalFile> getMedicalFilesForPatient(@PathVariable Long patientId) {
        return medicalFileService.getMedicalFilesForPatient(patientId);
    }

    @PostMapping("/create/{doctorId}/{patientId}")
    public MedicalFile createMedicalFile(@PathVariable Long doctorId, @PathVariable Long patientId, @RequestBody MedicalFile medicalFile) {
        if (doctorId == null || patientId == null) {
            throw new IllegalArgumentException("Doctor and Patient must be provided.");
        }

        // Retrieve doctor and patient from the database using their IDs
        User doctor = userRepository.findById(doctorId).orElseThrow(() -> new RuntimeException("Doctor not found"));
        User patient = userRepository.findById(patientId).orElseThrow(() -> new RuntimeException("Patient not found"));

        // Set the doctor and patient in the medical file
        medicalFile.setDoctor(doctor);
        medicalFile.setPatient(patient);


        // Save the medical file
        return medicalFileService.saveMedicalFile(doctorId, patientId, medicalFile);
    }

    @GetMapping("/allfiles")
    public List<MedicalFile> getMedicalFiles() {
        return medicalFileService.getMedicalFiles();
    }

    @PutMapping("/update/{medicalFileId}")
    public MedicalFile updateMedicalFile(@PathVariable Long medicalFileId, @RequestBody MedicalFile medicalFile) {
        return medicalFileService.updateMedicalFile(medicalFileId, medicalFile);
    }


}

package com.example.project.backend.controllers;

import com.example.project.backend.dto.PrescriptionDto;
import com.example.project.backend.entity.Prescription;
import com.example.project.backend.services.prescription.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prescriptions")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @PostMapping("/add")
    public ResponseEntity<Prescription> createPrescription(@RequestBody PrescriptionDto dto) {
        Prescription prescription = prescriptionService.createPrescription(dto);
        return ResponseEntity.ok(prescription);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Prescription>> getAll() {
        return ResponseEntity.ok(prescriptionService.getAllPrescriptions());
    }

    @GetMapping("/getby/{id}")
    public ResponseEntity<Prescription> getById(@PathVariable Long id) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionById(id));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Prescription>> getByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionsByPatientId(patientId));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Prescription>> getByDoctor(@PathVariable Long doctorId) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionsByDoctorId(doctorId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Prescription> updatePrescription(
            @PathVariable Long id,
            @RequestBody PrescriptionDto dto) {
        Prescription updated = prescriptionService.updatePrescription(id, dto);
        return ResponseEntity.ok(updated);
    }


}

package com.example.project.backend.services.prescription;

import com.example.project.backend.dto.MedicationDto;
import com.example.project.backend.dto.PrescriptionDto;
import com.example.project.backend.entity.Medication;
import com.example.project.backend.entity.Prescription;
import com.example.project.backend.entity.User;
import com.example.project.backend.repositories.MedicationRepository;
import com.example.project.backend.repositories.PrescriptionRepository;
import com.example.project.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrescriptionService {
    private final PrescriptionRepository prescriptionRepository;
    private final MedicationRepository medicationRepository;
    private final UserRepository userRepository;

    public Prescription createPrescription(PrescriptionDto dto) {
        User doctor = userRepository.findById(dto.getDoctorId()).orElseThrow(() -> new RuntimeException("Doctor not found"));
        User patient = userRepository.findById(dto.getPatientId()).orElseThrow(() -> new RuntimeException("Patient not found"));

        Prescription prescription = new Prescription();
        prescription.setDoctor(doctor);
        prescription.setPatient(patient);
        prescription.setSymptoms(dto.getSymptoms());

        List<Medication> meds = new ArrayList<>();
        boolean allAvailable = true;

        for (MedicationDto medDto : dto.getMedications()) {
            Medication medication = new Medication();
            medication.setName(medDto.getName());
            medication.setUsageInstructions(medDto.getUsage());
            medication.setDuration(medDto.getDuration());
            medication.setDelivered(medDto.isDelivered());
            medication.setPrescription(prescription);
            meds.add(medication);

            if (!medDto.isDelivered()) {
                allAvailable = false;
            }
        }

        prescription.setStatus(true);
        prescription.setMedications(meds);

        return prescriptionRepository.save(prescription);
    }

    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }

    public Prescription getPrescriptionById(Long id) {
        return prescriptionRepository.findById(id).orElseThrow(() -> new RuntimeException("Prescription not found"));
    }

    public List<Prescription> getPrescriptionsByPatientId(Long patientId) {
        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        return prescriptionRepository.findByPatient(patient);
    }

    public List<Prescription> getPrescriptionsByDoctorId(Long doctorId) {
        User doctor = userRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        return prescriptionRepository.findByDoctor(doctor);
    }

    @Transactional
    public Prescription updatePrescription(Long id, PrescriptionDto dto) {
        Prescription existing = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found"));

        // Update base fields
        existing.setSymptoms(dto.getSymptoms());

        User doctor = userRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        User patient = userRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        existing.setDoctor(doctor);
        existing.setPatient(patient);

        // Clear and replace medications properly
        existing.getMedications().clear();
        boolean allDelivered = true;

        for (MedicationDto medDto : dto.getMedications()) {
            Medication med = new Medication();
            med.setName(medDto.getName());
            med.setUsageInstructions(medDto.getUsage());
            med.setDuration(medDto.getDuration());
            med.setDelivered(medDto.isDelivered());
            med.setPrescription(existing);
            existing.getMedications().add(med);

            if (!medDto.isDelivered()) {
                allDelivered = false;
            }
        }

        existing.setStatus(dto.isStatus());


        return prescriptionRepository.save(existing);
    }


}

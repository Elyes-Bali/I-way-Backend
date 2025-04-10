package com.example.project.backend.services.medicalFile;

import com.example.project.backend.entity.MedicalFile;
import com.example.project.backend.entity.Posts;
import com.example.project.backend.entity.User;
import com.example.project.backend.repositories.MedicalFileRepository;
import com.example.project.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalFileServiceImpl {
    @Autowired
    private MedicalFileRepository medicalFileRepository;
    @Autowired
    private UserRepository userRepository;

    public List<MedicalFile> getMedicalFilesForDoctor(Long doctorId) {
        return medicalFileRepository.findByDoctorId(doctorId);
    }

    public List<MedicalFile> getMedicalFilesForPatient(Long patientId) {
        return medicalFileRepository.findByPatientId(patientId);
    }

    public MedicalFile  saveMedicalFile(Long doctorId, Long patientId, MedicalFile medicalFile) {
        // Retrieve doctor and patient from the database using their IDs
        User doctor = userRepository.findById(doctorId).orElseThrow(() -> new RuntimeException("Doctor not found"));
        User patient = userRepository.findById(patientId).orElseThrow(() -> new RuntimeException("Patient not found"));

        // Set the doctor and patient in the medical file
        medicalFile.setDoctor(doctor);
        medicalFile.setPatient(patient);

        // Save the medical file
        return medicalFileRepository.save(medicalFile);
    }

    public List<MedicalFile> getMedicalFiles() {
        return medicalFileRepository.findAll();
    }


    public MedicalFile updateMedicalFile(Long medicalFileId, MedicalFile updatedMedicalFile) {
        // Find the existing medical file
        MedicalFile existingMedicalFile = medicalFileRepository.findById(medicalFileId)
                .orElseThrow(() -> new RuntimeException("Medical File not found"));

        // Update the fields of the existing medical file
        existingMedicalFile.setSymptoms(updatedMedicalFile.getSymptoms());
        existingMedicalFile.setPrescriptions(updatedMedicalFile.getPrescriptions());
        existingMedicalFile.setAdditionalNotes(updatedMedicalFile.getAdditionalNotes());
        existingMedicalFile.setReasons(updatedMedicalFile.getReasons());
        existingMedicalFile.setBloodType(updatedMedicalFile.getBloodType());
        existingMedicalFile.setHeight(updatedMedicalFile.getHeight());
        existingMedicalFile.setWeight(updatedMedicalFile.getWeight());
        existingMedicalFile.setPreviousMedication(updatedMedicalFile.getPreviousMedication());

        // Save the updated medical file
        return medicalFileRepository.save(existingMedicalFile);
    }


}

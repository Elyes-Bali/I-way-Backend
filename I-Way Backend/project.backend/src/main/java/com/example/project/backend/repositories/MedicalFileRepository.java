package com.example.project.backend.repositories;

import com.example.project.backend.entity.MedicalFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalFileRepository extends JpaRepository<MedicalFile,Long> {
    List<MedicalFile> findByDoctorId(Long doctorId);
    List<MedicalFile> findByPatientId(Long patientId);
}

package com.example.project.backend.repositories;
import com.example.project.backend.entity.Prescription;
import com.example.project.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long>{
    List<Prescription> findByPatient(User patient);
    List<Prescription> findByDoctor(User doctor);
}

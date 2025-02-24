package com.example.project.backend.repositories;

import com.example.project.backend.entity.Reclamations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReclamationRepository extends JpaRepository<Reclamations,Long> {
}

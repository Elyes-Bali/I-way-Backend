package com.example.project.backend.services.reclamationsServ;

import com.example.project.backend.entity.Reclamations;

import java.util.List;

public interface ReclamationsService {
    void saveReclamation(String description, Long ownerId, String email);

    void updateReclamation(Long idReclamation , Reclamations q);
    Reclamations getReclamationbyId(Long idReclamation);
    List<Reclamations> findAllReclamation();
    void deleteReclamation(Long idReclamation);
}

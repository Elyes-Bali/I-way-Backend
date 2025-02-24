package com.example.project.backend.services.reclamationsServ;

import com.example.project.backend.entity.Reclamations;
import com.example.project.backend.repositories.ReclamationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ReclamationsServiceImpl implements ReclamationsService{
    @Autowired
    private ReclamationRepository reclamationRepository;

    public void saveReclamation(String description, Long ownerId, String email) {
        Reclamations reclamation = new Reclamations();
        reclamation.setDescription(description);
        reclamation.setOwnerId(ownerId);
        reclamation.setEmail(email);
        reclamationRepository.save(reclamation);
    }

    @Override
    public void updateReclamation(Long idReclamation, Reclamations q) {
        q.setIdReclamation(idReclamation);
        reclamationRepository.save(q);
    }

    @Override
    public Reclamations getReclamationbyId(Long idReclamation) {
        return reclamationRepository.findById(idReclamation).get();
    }

    @Override
    public List<Reclamations> findAllReclamation() {
        return reclamationRepository.findAll();
    }

    @Override
    public void deleteReclamation(Long idReclamation) {
        reclamationRepository.deleteById(idReclamation);
    }
}

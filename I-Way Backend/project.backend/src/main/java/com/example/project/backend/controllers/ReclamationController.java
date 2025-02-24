package com.example.project.backend.controllers;

import com.example.project.backend.entity.Reclamations;
import com.example.project.backend.services.reclamationsServ.ReclamationsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/reclamations")
public class ReclamationController {
    @Autowired
    private ReclamationsService reclamationService;

    @PostMapping("/save")
    public void saveReclamation(@RequestBody Reclamations reclamation) {
        System.out.println("Saving reclamation: " + reclamation);
        reclamationService.saveReclamation(reclamation.getDescription(), reclamation.getOwnerId(), reclamation.getEmail());
    }

    @PutMapping("/updaterec/{idReclamation}")
    public void updateReclamation(@PathVariable Long idReclamation,@RequestBody Reclamations q){
        reclamationService.updateReclamation(idReclamation,q);
    }
    @GetMapping("/getrecbyid/{idReclamation}")
    public Reclamations getReclamationbyId(@PathVariable Long idReclamation) {
        return reclamationService.getReclamationbyId(idReclamation);
    }

    @GetMapping("/getallrecs")
    public List<Reclamations> findAllReclamation(){
        return reclamationService.findAllReclamation();
    }
    @DeleteMapping("/deleterec/{idReclamation}")
    public  void  deleteReclamation(@PathVariable Long idReclamation) {
        reclamationService.deleteReclamation(idReclamation);
    }
}

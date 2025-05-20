package com.example.project.backend.services.reclamationsServ;

import com.example.project.backend.entity.Reclamations;
import com.example.project.backend.repositories.ReclamationRepository;
import com.example.project.backend.services.mailing.MailService;
import com.example.project.backend.services.mailing.MailingServiceRec;
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

    @Autowired
    private MailingServiceRec mailService;

    public void saveReclamation(String description, Long ownerId, String email) {
        Reclamations reclamation = new Reclamations();
        reclamation.setDescription(description);
        reclamation.setOwnerId(ownerId);
        reclamation.setEmail(email);
        reclamationRepository.save(reclamation);

        // Send the email to a fixed address
        String to = "heallinkteam@gmail.com";
        String subject = "New Reclamation";
        String htmlContent = """
        <html>
        <body style="font-family: Arial, sans-serif; background-color: #f8f8f8; padding: 20px;">
            <div style="background-color: #ffffff; border-radius: 10px; padding: 30px; max-width: 600px; margin: auto; box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);">
                <h2 style="color: #2c3e50;">📢 You Have a New Reclamation</h2>
                <p style="font-size: 16px; color: #333;">
                    Hello,<br><br>
                    A new reclamation has been submitted with the following description:
                </p>
                <blockquote style="background-color: #f0f0f0; padding: 15px; border-left: 5px solid #3498db; margin: 20px 0;">
                    <em>%s</em>
                </blockquote>
                <p style="font-size: 16px; color: #333;">
                    Please review it at your earliest convenience.<br><br>
                    — HealLink
                </p>
            </div>
        </body>
        </html>
    """.formatted(description);
        mailService.sendSimpleMessage(to, subject, htmlContent);
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

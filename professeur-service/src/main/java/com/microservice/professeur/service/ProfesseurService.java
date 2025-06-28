package com.microservice.professeur.service;

import com.microservice.professeur.model.Professeur;
import com.microservice.professeur.repository.ProfesseurRepository;
import com.microservice.professeur.event.ProfesseurEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProfesseurService {
    
    @Autowired
    private ProfesseurRepository professeurRepository;
    
    @Autowired
    private KafkaTemplate<String, ProfesseurEvent> kafkaTemplate;
    
    private static final String PROFESSEUR_TOPIC = "professeur-events";
    
    public List<Professeur> getAllProfesseurs() {
        return professeurRepository.findAll();
    }
    
    public Optional<Professeur> getProfesseurById(String id) {
        return professeurRepository.findById(id);
    }
    
    public Optional<Professeur> getProfesseurByEmail(String email) {
        return professeurRepository.findByEmail(email);
    }
    
    public List<Professeur> getProfesseursBySpecialite(String specialite) {
        return professeurRepository.findBySpecialite(specialite);
    }
    
    public List<Professeur> searchProfesseursByName(String nom) {
        return professeurRepository.findByNomContainingIgnoreCase(nom);
    }
    
    public Professeur createProfesseur(Professeur professeur) {
        if (professeurRepository.existsByEmail(professeur.getEmail())) {
            throw new RuntimeException("Un professeur avec cet email existe déjà");
        }
        
        professeur.setDateCreation(LocalDateTime.now());
        professeur.setDateModification(LocalDateTime.now());
        
        Professeur savedProfesseur = professeurRepository.save(professeur);
        
        // Publier l'événement Kafka
        ProfesseurEvent event = new ProfesseurEvent("PROFESSEUR_CREATED", savedProfesseur);
        kafkaTemplate.send(PROFESSEUR_TOPIC, event);
        
        return savedProfesseur;
    }
    
    public Professeur updateProfesseur(String id, Professeur professeurDetails) {
        Optional<Professeur> existingProfesseur = professeurRepository.findById(id);
        
        if (existingProfesseur.isPresent()) {
            Professeur professeur = existingProfesseur.get();
            
            // Vérifier si l'email change et s'il existe déjà
            if (!professeur.getEmail().equals(professeurDetails.getEmail()) && 
                professeurRepository.existsByEmail(professeurDetails.getEmail())) {
                throw new RuntimeException("Un professeur avec cet email existe déjà");
            }
            
            professeur.setNom(professeurDetails.getNom());
            professeur.setPrenom(professeurDetails.getPrenom());
            professeur.setEmail(professeurDetails.getEmail());
            professeur.setSpecialite(professeurDetails.getSpecialite());
            professeur.setTelephone(professeurDetails.getTelephone());
            professeur.setAdresse(professeurDetails.getAdresse());
            professeur.setDateEmbauche(professeurDetails.getDateEmbauche());
            professeur.setDateModification(LocalDateTime.now());
            
            Professeur updatedProfesseur = professeurRepository.save(professeur);
            
            // Publier l'événement Kafka
            ProfesseurEvent event = new ProfesseurEvent("PROFESSEUR_UPDATED", updatedProfesseur);
            kafkaTemplate.send(PROFESSEUR_TOPIC, event);
            
            return updatedProfesseur;
        } else {
            throw new RuntimeException("Professeur non trouvé avec l'id: " + id);
        }
    }
    
    public void deleteProfesseur(String id) {
        Optional<Professeur> professeur = professeurRepository.findById(id);
        
        if (professeur.isPresent()) {
            professeurRepository.deleteById(id);
            
            // Publier l'événement Kafka
            ProfesseurEvent event = new ProfesseurEvent("PROFESSEUR_DELETED", professeur.get());
            kafkaTemplate.send(PROFESSEUR_TOPIC, event);
        } else {
            throw new RuntimeException("Professeur non trouvé avec l'id: " + id);
        }
    }
    
    public long countProfesseursBySpecialite(String specialite) {
        return professeurRepository.countBySpecialite(specialite);
    }
    
    public boolean existsByEmail(String email) {
        return professeurRepository.existsByEmail(email);
    }
} 
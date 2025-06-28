package com.microservice.classe.service;

import com.microservice.classe.model.Classe;
import com.microservice.classe.repository.ClasseRepository;
import com.microservice.classe.client.ProfesseurClient;
import com.microservice.classe.dto.ProfesseurDTO;
import com.microservice.classe.event.ClasseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ClasseService {
    
    @Autowired
    private ClasseRepository classeRepository;
    
    @Autowired
    private ProfesseurClient professeurClient;
    
    @Autowired
    private KafkaTemplate<String, ClasseEvent> kafkaTemplate;
    
    private static final String CLASSE_TOPIC = "classe-events";
    
    public List<Classe> getAllClasses() {
        return classeRepository.findAll();
    }
    
    public Optional<Classe> getClasseById(Long id) {
        return classeRepository.findById(id);
    }
    
    public Optional<Classe> getClasseByNom(String nom) {
        return classeRepository.findByNom(nom);
    }
    
    public List<Classe> getClassesByNiveau(String niveau) {
        return classeRepository.findByNiveau(niveau);
    }
    
    public List<Classe> getClassesByProfesseurId(String professeurId) {
        return classeRepository.findByProfesseurId(professeurId);
    }
    
    public List<Classe> searchClassesByName(String nom) {
        return classeRepository.findByNomContaining(nom);
    }
    
    public List<Classe> getClassesWithMinEtudiants(Integer minEtudiants) {
        return classeRepository.findByNombreEtudiantsGreaterThanEqual(minEtudiants);
    }
    
    public List<Classe> getClassesByNiveauAndMaxEtudiants(String niveau, Integer maxEtudiants) {
        return classeRepository.findByNiveauAndNombreEtudiantsLessThanEqual(niveau, maxEtudiants);
    }
    
    public Classe createClasse(Classe classe) {
        if (classeRepository.existsByNom(classe.getNom())) {
            throw new RuntimeException("Une classe avec ce nom existe déjà");
        }
        
        // Vérifier si le professeur existe si un professeurId est fourni
        if (classe.getProfesseurId() != null && !classe.getProfesseurId().isEmpty()) {
            try {
                professeurClient.getProfesseurById(classe.getProfesseurId());
            } catch (Exception e) {
                throw new RuntimeException("Le professeur avec l'ID " + classe.getProfesseurId() + " n'existe pas");
            }
        }
        
        classe.setDateCreation(LocalDateTime.now());
        classe.setDateModification(LocalDateTime.now());
        
        Classe savedClasse = classeRepository.save(classe);
        
        // Publier l'événement Kafka
        ClasseEvent event = new ClasseEvent("CLASSE_CREATED", savedClasse);
        kafkaTemplate.send(CLASSE_TOPIC, event);
        
        return savedClasse;
    }
    
    public Classe updateClasse(Long id, Classe classeDetails) {
        Optional<Classe> existingClasse = classeRepository.findById(id);
        
        if (existingClasse.isPresent()) {
            Classe classe = existingClasse.get();
            
            // Vérifier si le nom change et s'il existe déjà
            if (!classe.getNom().equals(classeDetails.getNom()) && 
                classeRepository.existsByNom(classeDetails.getNom())) {
                throw new RuntimeException("Une classe avec ce nom existe déjà");
            }
            
            // Vérifier si le professeur existe si un professeurId est fourni
            if (classeDetails.getProfesseurId() != null && !classeDetails.getProfesseurId().isEmpty()) {
                try {
                    professeurClient.getProfesseurById(classeDetails.getProfesseurId());
                } catch (Exception e) {
                    throw new RuntimeException("Le professeur avec l'ID " + classeDetails.getProfesseurId() + " n'existe pas");
                }
            }
            
            classe.setNom(classeDetails.getNom());
            classe.setNiveau(classeDetails.getNiveau());
            classe.setNombreEtudiants(classeDetails.getNombreEtudiants());
            classe.setDescription(classeDetails.getDescription());
            classe.setSalle(classeDetails.getSalle());
            classe.setProfesseurId(classeDetails.getProfesseurId());
            classe.setDateModification(LocalDateTime.now());
            
            Classe updatedClasse = classeRepository.save(classe);
            
            // Publier l'événement Kafka
            ClasseEvent event = new ClasseEvent("CLASSE_UPDATED", updatedClasse);
            kafkaTemplate.send(CLASSE_TOPIC, event);
            
            return updatedClasse;
        } else {
            throw new RuntimeException("Classe non trouvée avec l'id: " + id);
        }
    }
    
    public void deleteClasse(Long id) {
        Optional<Classe> classe = classeRepository.findById(id);
        
        if (classe.isPresent()) {
            classeRepository.deleteById(id);
            
            // Publier l'événement Kafka
            ClasseEvent event = new ClasseEvent("CLASSE_DELETED", classe.get());
            kafkaTemplate.send(CLASSE_TOPIC, event);
        } else {
            throw new RuntimeException("Classe non trouvée avec l'id: " + id);
        }
    }
    
    public ProfesseurDTO getProfesseurByClasseId(Long classeId) {
        Optional<Classe> classe = classeRepository.findById(classeId);
        
        if (classe.isPresent() && classe.get().getProfesseurId() != null) {
            try {
                return professeurClient.getProfesseurById(classe.get().getProfesseurId());
            } catch (Exception e) {
                throw new RuntimeException("Erreur lors de la récupération du professeur: " + e.getMessage());
            }
        } else {
            throw new RuntimeException("Classe non trouvée ou sans professeur assigné");
        }
    }
    
    public long countClassesByNiveau(String niveau) {
        return classeRepository.countByNiveau(niveau);
    }
    
    public Double getAverageNombreEtudiants() {
        return classeRepository.getAverageNombreEtudiants();
    }
    
    public boolean existsByNom(String nom) {
        return classeRepository.existsByNom(nom);
    }
} 
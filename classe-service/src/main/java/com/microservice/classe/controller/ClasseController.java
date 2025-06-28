package com.microservice.classe.controller;

import com.microservice.classe.model.Classe;
import com.microservice.classe.service.ClasseService;
import com.microservice.classe.dto.ProfesseurDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/classes")
@CrossOrigin(origins = "*")
public class ClasseController {
    
    @Autowired
    private ClasseService classeService;
    
    @GetMapping
    public ResponseEntity<List<Classe>> getAllClasses() {
        List<Classe> classes = classeService.getAllClasses();
        return ResponseEntity.ok(classes);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Classe> getClasseById(@PathVariable Long id) {
        Optional<Classe> classe = classeService.getClasseById(id);
        return classe.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/nom/{nom}")
    public ResponseEntity<Classe> getClasseByNom(@PathVariable String nom) {
        Optional<Classe> classe = classeService.getClasseByNom(nom);
        return classe.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/niveau/{niveau}")
    public ResponseEntity<List<Classe>> getClassesByNiveau(@PathVariable String niveau) {
        List<Classe> classes = classeService.getClassesByNiveau(niveau);
        return ResponseEntity.ok(classes);
    }
    
    @GetMapping("/professeur/{professeurId}")
    public ResponseEntity<List<Classe>> getClassesByProfesseurId(@PathVariable String professeurId) {
        List<Classe> classes = classeService.getClassesByProfesseurId(professeurId);
        return ResponseEntity.ok(classes);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Classe>> searchClassesByName(@RequestParam String nom) {
        List<Classe> classes = classeService.searchClassesByName(nom);
        return ResponseEntity.ok(classes);
    }
    
    @GetMapping("/min-etudiants/{minEtudiants}")
    public ResponseEntity<List<Classe>> getClassesWithMinEtudiants(@PathVariable Integer minEtudiants) {
        List<Classe> classes = classeService.getClassesWithMinEtudiants(minEtudiants);
        return ResponseEntity.ok(classes);
    }
    
    @GetMapping("/niveau/{niveau}/max-etudiants/{maxEtudiants}")
    public ResponseEntity<List<Classe>> getClassesByNiveauAndMaxEtudiants(@PathVariable String niveau, 
                                                                        @PathVariable Integer maxEtudiants) {
        List<Classe> classes = classeService.getClassesByNiveauAndMaxEtudiants(niveau, maxEtudiants);
        return ResponseEntity.ok(classes);
    }
    
    @PostMapping
    public ResponseEntity<Classe> createClasse(@Valid @RequestBody Classe classe) {
        try {
            Classe createdClasse = classeService.createClasse(classe);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdClasse);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Classe> updateClasse(@PathVariable Long id, 
                                              @Valid @RequestBody Classe classeDetails) {
        try {
            Classe updatedClasse = classeService.updateClasse(id, classeDetails);
            return ResponseEntity.ok(updatedClasse);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClasse(@PathVariable Long id) {
        try {
            classeService.deleteClasse(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Endpoint OpenFeign pour récupérer le professeur d'une classe
    @GetMapping("/{id}/professeur")
    public ResponseEntity<ProfesseurDTO> getProfesseurByClasseId(@PathVariable Long id) {
        try {
            ProfesseurDTO professeur = classeService.getProfesseurByClasseId(id);
            return ResponseEntity.ok(professeur);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/count/niveau/{niveau}")
    public ResponseEntity<Long> countClassesByNiveau(@PathVariable String niveau) {
        long count = classeService.countClassesByNiveau(niveau);
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/stats/average-etudiants")
    public ResponseEntity<Double> getAverageNombreEtudiants() {
        Double average = classeService.getAverageNombreEtudiants();
        return ResponseEntity.ok(average);
    }
    
    @GetMapping("/exists/nom/{nom}")
    public ResponseEntity<Boolean> existsByNom(@PathVariable String nom) {
        boolean exists = classeService.existsByNom(nom);
        return ResponseEntity.ok(exists);
    }
    
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Classe Service is running!");
    }
} 
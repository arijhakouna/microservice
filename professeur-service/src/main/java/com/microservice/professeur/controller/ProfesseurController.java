package com.microservice.professeur.controller;

import com.microservice.professeur.model.Professeur;
import com.microservice.professeur.service.ProfesseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/professeurs")
@CrossOrigin(origins = "*")
public class ProfesseurController {
    
    @Autowired
    private ProfesseurService professeurService;
    
    @GetMapping
    public ResponseEntity<List<Professeur>> getAllProfesseurs() {
        List<Professeur> professeurs = professeurService.getAllProfesseurs();
        return ResponseEntity.ok(professeurs);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Professeur> getProfesseurById(@PathVariable String id) {
        Optional<Professeur> professeur = professeurService.getProfesseurById(id);
        return professeur.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<Professeur> getProfesseurByEmail(@PathVariable String email) {
        Optional<Professeur> professeur = professeurService.getProfesseurByEmail(email);
        return professeur.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/specialite/{specialite}")
    public ResponseEntity<List<Professeur>> getProfesseursBySpecialite(@PathVariable String specialite) {
        List<Professeur> professeurs = professeurService.getProfesseursBySpecialite(specialite);
        return ResponseEntity.ok(professeurs);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Professeur>> searchProfesseursByName(@RequestParam String nom) {
        List<Professeur> professeurs = professeurService.searchProfesseursByName(nom);
        return ResponseEntity.ok(professeurs);
    }
    
    @PostMapping
    public ResponseEntity<Professeur> createProfesseur(@Valid @RequestBody Professeur professeur) {
        try {
            Professeur createdProfesseur = professeurService.createProfesseur(professeur);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProfesseur);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Professeur> updateProfesseur(@PathVariable String id, 
                                                      @Valid @RequestBody Professeur professeurDetails) {
        try {
            Professeur updatedProfesseur = professeurService.updateProfesseur(id, professeurDetails);
            return ResponseEntity.ok(updatedProfesseur);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfesseur(@PathVariable String id) {
        try {
            professeurService.deleteProfesseur(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/count/specialite/{specialite}")
    public ResponseEntity<Long> countProfesseursBySpecialite(@PathVariable String specialite) {
        long count = professeurService.countProfesseursBySpecialite(specialite);
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Boolean> existsByEmail(@PathVariable String email) {
        boolean exists = professeurService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }
    
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Professeur Service is running!");
    }
} 
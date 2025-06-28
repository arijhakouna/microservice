package com.microservice.professeur.repository;

import com.microservice.professeur.model.Professeur;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfesseurRepository extends MongoRepository<Professeur, String> {
    
    Optional<Professeur> findByEmail(String email);
    
    List<Professeur> findBySpecialite(String specialite);
    
    List<Professeur> findByNomContainingIgnoreCase(String nom);
    
    @Query("{'nom': {$regex: ?0, $options: 'i'}, 'prenom': {$regex: ?1, $options: 'i'}}")
    List<Professeur> findByNomAndPrenomContainingIgnoreCase(String nom, String prenom);
    
    boolean existsByEmail(String email);
    
    long countBySpecialite(String specialite);
} 
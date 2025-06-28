package com.microservice.classe.repository;

import com.microservice.classe.model.Classe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClasseRepository extends JpaRepository<Classe, Long> {
    
    Optional<Classe> findByNom(String nom);
    
    List<Classe> findByNiveau(String niveau);
    
    List<Classe> findByProfesseurId(String professeurId);
    
    @Query("SELECT c FROM Classe c WHERE c.nom LIKE %:nom%")
    List<Classe> findByNomContaining(@Param("nom") String nom);
    
    @Query("SELECT c FROM Classe c WHERE c.nombreEtudiants >= :minEtudiants")
    List<Classe> findByNombreEtudiantsGreaterThanEqual(@Param("minEtudiants") Integer minEtudiants);
    
    @Query("SELECT c FROM Classe c WHERE c.niveau = :niveau AND c.nombreEtudiants <= :maxEtudiants")
    List<Classe> findByNiveauAndNombreEtudiantsLessThanEqual(@Param("niveau") String niveau, 
                                                           @Param("maxEtudiants") Integer maxEtudiants);
    
    boolean existsByNom(String nom);
    
    long countByNiveau(String niveau);
    
    @Query("SELECT AVG(c.nombreEtudiants) FROM Classe c")
    Double getAverageNombreEtudiants();
} 
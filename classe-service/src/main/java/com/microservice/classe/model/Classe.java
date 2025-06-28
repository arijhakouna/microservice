package com.microservice.classe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "classes")
public class Classe {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Le nom de la classe est obligatoire")
    @Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
    @Column(unique = true, nullable = false)
    private String nom;
    
    @NotBlank(message = "Le niveau est obligatoire")
    private String niveau;
    
    @NotNull(message = "Le nombre d'étudiants est obligatoire")
    private Integer nombreEtudiants;
    
    private String description;
    private String salle;
    
    @Column(name = "professeur_id")
    private String professeurId;
    
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    
    @Column(name = "date_modification")
    private LocalDateTime dateModification;
    
    // Constructeurs
    public Classe() {
        this.dateCreation = LocalDateTime.now();
        this.dateModification = LocalDateTime.now();
    }
    
    public Classe(String nom, String niveau, Integer nombreEtudiants) {
        this();
        this.nom = nom;
        this.niveau = niveau;
        this.nombreEtudiants = nombreEtudiants;
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getNiveau() {
        return niveau;
    }
    
    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }
    
    public Integer getNombreEtudiants() {
        return nombreEtudiants;
    }
    
    public void setNombreEtudiants(Integer nombreEtudiants) {
        this.nombreEtudiants = nombreEtudiants;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getSalle() {
        return salle;
    }
    
    public void setSalle(String salle) {
        this.salle = salle;
    }
    
    public String getProfesseurId() {
        return professeurId;
    }
    
    public void setProfesseurId(String professeurId) {
        this.professeurId = professeurId;
    }
    
    public LocalDateTime getDateCreation() {
        return dateCreation;
    }
    
    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }
    
    public LocalDateTime getDateModification() {
        return dateModification;
    }
    
    public void setDateModification(LocalDateTime dateModification) {
        this.dateModification = dateModification;
    }
    
    @Override
    public String toString() {
        return "Classe{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", niveau='" + niveau + '\'' +
                ", nombreEtudiants=" + nombreEtudiants +
                ", description='" + description + '\'' +
                ", salle='" + salle + '\'' +
                ", professeurId='" + professeurId + '\'' +
                ", dateCreation=" + dateCreation +
                ", dateModification=" + dateModification +
                '}';
    }
} 
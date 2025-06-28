package com.microservice.classe.dto;

import java.time.LocalDateTime;

public class ProfesseurDTO {
    
    private String id;
    private String nom;
    private String prenom;
    private String email;
    private String specialite;
    private String telephone;
    private String adresse;
    private LocalDateTime dateEmbauche;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
    
    // Constructeurs
    public ProfesseurDTO() {}
    
    public ProfesseurDTO(String id, String nom, String prenom, String email, String specialite) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.specialite = specialite;
    }
    
    // Getters et Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getPrenom() {
        return prenom;
    }
    
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getSpecialite() {
        return specialite;
    }
    
    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }
    
    public String getTelephone() {
        return telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    public String getAdresse() {
        return adresse;
    }
    
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    
    public LocalDateTime getDateEmbauche() {
        return dateEmbauche;
    }
    
    public void setDateEmbauche(LocalDateTime dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
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
        return "ProfesseurDTO{" +
                "id='" + id + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", specialite='" + specialite + '\'' +
                ", telephone='" + telephone + '\'' +
                ", adresse='" + adresse + '\'' +
                ", dateEmbauche=" + dateEmbauche +
                ", dateCreation=" + dateCreation +
                ", dateModification=" + dateModification +
                '}';
    }
} 
package com.microservice.professeur.event;

import com.microservice.professeur.model.Professeur;
import java.time.LocalDateTime;

public class ProfesseurEvent {
    
    private String eventType;
    private Professeur professeur;
    private LocalDateTime timestamp;
    
    public ProfesseurEvent() {
        this.timestamp = LocalDateTime.now();
    }
    
    public ProfesseurEvent(String eventType, Professeur professeur) {
        this();
        this.eventType = eventType;
        this.professeur = professeur;
    }
    
    // Getters et Setters
    public String getEventType() {
        return eventType;
    }
    
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
    
    public Professeur getProfesseur() {
        return professeur;
    }
    
    public void setProfesseur(Professeur professeur) {
        this.professeur = professeur;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    @Override
    public String toString() {
        return "ProfesseurEvent{" +
                "eventType='" + eventType + '\'' +
                ", professeur=" + professeur +
                ", timestamp=" + timestamp +
                '}';
    }
} 
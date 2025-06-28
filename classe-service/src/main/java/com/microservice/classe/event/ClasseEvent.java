package com.microservice.classe.event;

import com.microservice.classe.model.Classe;
import java.time.LocalDateTime;

public class ClasseEvent {
    
    private String eventType;
    private Classe classe;
    private LocalDateTime timestamp;
    
    public ClasseEvent() {
        this.timestamp = LocalDateTime.now();
    }
    
    public ClasseEvent(String eventType, Classe classe) {
        this();
        this.eventType = eventType;
        this.classe = classe;
    }
    
    // Getters et Setters
    public String getEventType() {
        return eventType;
    }
    
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
    
    public Classe getClasse() {
        return classe;
    }
    
    public void setClasse(Classe classe) {
        this.classe = classe;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    @Override
    public String toString() {
        return "ClasseEvent{" +
                "eventType='" + eventType + '\'' +
                ", classe=" + classe +
                ", timestamp=" + timestamp +
                '}';
    }
} 
package com.microservice.classe.client;

import com.microservice.classe.dto.ProfesseurDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "professeur-service")
public interface ProfesseurClient {
    
    @GetMapping("/api/professeurs/{id}")
    ProfesseurDTO getProfesseurById(@PathVariable String id);
    
    @GetMapping("/api/professeurs/email/{email}")
    ProfesseurDTO getProfesseurByEmail(@PathVariable String email);
} 
package com.example.pdfGenerator.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class HealthCheck {
    @GetMapping("/health")
    public String healthCheck(){
        return "everything is fine up and working !";
    }
}

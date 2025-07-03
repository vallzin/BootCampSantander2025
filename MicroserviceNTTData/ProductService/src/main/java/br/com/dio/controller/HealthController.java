package br.com.dio.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/")
    public String home(){
        return "Serviço de Produtos Online";
    }

    @GetMapping("/health")
    public String healthCheck(){
        return "Status: OK!";
    }

    @GetMapping("/actuator/health")
    public String actuatorHealth(){
        return "{\"status\":\"UP\"}";
    }

}

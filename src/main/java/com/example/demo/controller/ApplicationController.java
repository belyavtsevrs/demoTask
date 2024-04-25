package com.example.demo.controller;

import com.example.demo.DTO.ApplicationDTO;
import com.example.demo.DTO.CreateApplicationDTO;
import com.example.demo.service.ApplicationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;
    @GetMapping("/")
    public List<ApplicationDTO> home(){
        return applicationService.allDTO();
    }
    @PostMapping("/create-application")
    public ResponseEntity<?> createApp(@RequestBody CreateApplicationDTO application, Principal principal){
        applicationService.create(application,principal);
        log.info("appDTO = {}",application);
        return ResponseEntity.ok().body(application);
    }
}

package com.example.demo.controller;

import com.example.demo.DTO.ApplicationDTO;
import com.example.demo.model.Application;
import com.example.demo.model.Users;
import com.example.demo.service.ApplicationService;
import com.example.demo.service.UserService;
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
    private final UserService userService;
    private final ApplicationService applicationService;

    @GetMapping("/")
    public List<Application> findAll(){
        return applicationService.findAll();
    }

    @PostMapping("/create-application")
    public ResponseEntity<?> createApp(@RequestBody ApplicationDTO application, Principal principal){
        applicationService.create(application,principal);
        log.info("app = {}",application);
        return ResponseEntity.ok().body(application);
    }
    @GetMapping("/users")
    public List<Users> home(){
        return userService.findAll();
    }

}

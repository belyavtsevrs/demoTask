package com.example.demo.controller;

import com.example.demo.DTO.ApplicationDTO;
import com.example.demo.DTO.CreateApplicationDTO;
import com.example.demo.DTO.UserDTO;
import com.example.demo.model.Users;
import com.example.demo.service.ApplicationService;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@Slf4j
@RestController
@AllArgsConstructor
@Tag(name="Application Controller", description="Controller that allows to create applications and check it")
public class ApplicationController {
    private final ApplicationService applicationService;
    private final UserService userService;

    @Operation(summary = "applications list")
    @GetMapping("/")
    public List<ApplicationDTO> home(){
        return applicationService.allDTO();
    }

    @Operation(summary = "users list")
    @GetMapping("/users")
    public List<UserDTO> users(){
        return userService.allDTO();
    }

    @Operation(summary = "create application")
    @PostMapping("/create-application")
    public ResponseEntity<?> createApp(@RequestBody CreateApplicationDTO application, Principal principal){
        applicationService.create(application,principal);
        log.info("appDTO = {}",application);
        return ResponseEntity.ok().body(application);
    }
}

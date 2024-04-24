package com.example.demo.controller;

import com.example.demo.DTO.RegistrationDTO;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@AllArgsConstructor
public class SecurityController {
    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@Valid @RequestBody RegistrationDTO registrationDTO) {
        userService.create(registrationDTO);
        return ResponseEntity.ok().body(registrationDTO.toString());
    }

    @PostMapping("/add-avatar")
    public ResponseEntity<?> addAvatar(Principal principal , @RequestParam("avatar")MultipartFile multipartFile){
        userService.addImage(principal,multipartFile);
        return ResponseEntity.ok().body("done");
    }
}
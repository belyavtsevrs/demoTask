package com.example.demo.controller;

import com.example.demo.DTO.CreateUserDTO;
import com.example.demo.DTO.UserDTO;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@AllArgsConstructor
@Tag(name="Security Controller", description="Controller that contains registration and authorization methods")
public class SecurityController {
    private final UserService userService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok().body(userService.login(userDTO));
    }

    @Operation(summary = "registration user method")
    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
        userService.create(createUserDTO);
        return ResponseEntity.ok().body("authorized");
    }

    @Operation(summary = "adding avatar")
    @PostMapping("/add-avatar")
    public ResponseEntity<?> addAvatar(Principal principal ,
                                       @RequestParam("avatar")MultipartFile multipartFile
    ){
        userService.addImage(principal,multipartFile);
        return ResponseEntity.ok().body("done");
    }

}

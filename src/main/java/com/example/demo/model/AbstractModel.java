package com.example.demo.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@MappedSuperclass
public class AbstractModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate created_at;

    @PrePersist
    private void init(){
        created_at = LocalDate.now();
    }
}

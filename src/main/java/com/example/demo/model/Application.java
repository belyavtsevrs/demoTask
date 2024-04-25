package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Application extends AbstractModel{
    @NotNull
    private String title;
    @Min(1)
    private Long count;
    @NotNull
    private String address;
    @NotNull
    private String phoneNumber;
    @JsonBackReference
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    private Users user;
}

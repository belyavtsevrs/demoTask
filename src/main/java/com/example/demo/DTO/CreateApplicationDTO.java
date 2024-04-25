package com.example.demo.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CreateApplicationDTO {
    @NotNull
    private String title;
    private Long count;
    @NotNull
    private String address;
    @NotNull
    private String phoneNumber;
}

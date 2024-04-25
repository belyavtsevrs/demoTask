package com.example.demo.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@ToString
public class ApplicationDTO {
    @NotNull
    private String title;
    private Long count;
    @NotNull
    private String address;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String creator;
}

package com.example.demo.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CreateApplicationDTO {
    @NotNull
    @Schema(name = "title", example = "title123")
    private String title;
    @Min(1)
    @Schema(name = "count", example = "1")
    private Long count;
    @NotNull
    @Schema(name = "address", example = "address1231231")
    private String address;
    @NotNull
    @Schema(name = "phoneNumber", example = "1232312")
    private String phoneNumber;
}

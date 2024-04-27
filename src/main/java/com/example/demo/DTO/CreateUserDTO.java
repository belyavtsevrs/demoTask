package com.example.demo.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CreateUserDTO {
    @Email
    @Schema(name = "email", example = "brs@gmail.com")
    private String email;
    @Size(min = 3, max = 10)
    @Schema(name = "login", example = "brs")
    private String login;
    @Size(min = 3, max = 10)
    @Schema(name = "password", example = "123")
    private String password;
    @Size(min = 3, max = 10)
    @Schema(name = "confirmPassword", example = "123")
    private String confirmPassword;
    @Size(min = 3, max = 10)
    @Schema(name = "name", example = "bebra")
    private String name;
    @Size(min = 3, max = 10)
    @Schema(name = "lastName", example = "bebra")
    private String lastName;
}

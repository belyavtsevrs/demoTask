package com.example.demo.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UserDTO {
    @Email
    private String email;
    @Size(min = 3,max = 10)
    private String login;
    @Size(min = 3,max = 10)
    private String password;
    @Size(min = 3, max = 10)
    private String name;
    @Size(min = 3, max = 10)
    private String lastName;
}

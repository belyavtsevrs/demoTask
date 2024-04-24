package com.example.demo.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ApplicationDTO {
    private String title;
    private Long count;
    private String address;
    private String phoneNumber;
}

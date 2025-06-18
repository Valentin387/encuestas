package com.katadavivienda.encuestas.data.dto;

import lombok.Data;

@Data
public class UserDto {
    private String email;
    private String firstname;
    private String lastname;
    private String password;
    private String company;
}
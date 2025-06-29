package com.katadavivienda.encuestas.data.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
public class User {
    @Id
    private String id;
    private String email;
    private String firstname;
    private String lastname;
    private String password;
    private String company;
}

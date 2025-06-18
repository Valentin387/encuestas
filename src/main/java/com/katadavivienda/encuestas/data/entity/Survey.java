package com.katadavivienda.encuestas.data.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "surveys")
@Data
public class Survey {
    @Id
    private String id;
    private String userId;
    private String company;
    private String title;
    private String description;
    private List<Question> questions;
    private LocalDateTime createdAt;

    @Data
    public static class Question {
        private String questionId;
        private String questionText;
        private String type; // "age", "rating", "text"
        private boolean required;
    }
}
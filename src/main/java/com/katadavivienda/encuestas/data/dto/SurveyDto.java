package com.katadavivienda.encuestas.data.dto;

import lombok.Data;

import java.util.List;

@Data
public class SurveyDto {
    private String id;
    private String company;
    private String title;
    private String description;
    private List<QuestionDto> questions;

    @Data
    public static class QuestionDto {
        private String questionText;
        private String type; // "age", "rating", "text"
        private boolean required;
    }
}
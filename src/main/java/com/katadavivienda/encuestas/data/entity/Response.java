package com.katadavivienda.encuestas.data.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "responses")
@Data
public class Response {
    @Id
    private String id;
    private String surveyId;
    private Respondent respondent;
    private List<Answer> answers;
    private LocalDateTime submittedAt;

    @Data
    public static class Respondent {
        private String email;
        private String firstname;
        private String lastname;
    }

    @Data
    public static class Answer {
        private String questionId;
        private Object answer; // Integer for age/rating, String for text
    }
}
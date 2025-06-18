package com.katadavivienda.encuestas.data.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseDto {
    private RespondentDto respondent;
    private List<AnswerDto> answers;

    @Data
    public static class RespondentDto {
        private String email;
        private String firstname;
        private String lastname;
    }

    @Data
    public static class AnswerDto {
        private String questionId;
        private Object answer; // Integer for age/rating, String for text
    }
}
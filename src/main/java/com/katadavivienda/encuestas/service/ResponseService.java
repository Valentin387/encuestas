package com.katadavivienda.encuestas.service;

import com.katadavivienda.encuestas.data.dto.ResponseDto;
import com.katadavivienda.encuestas.data.entity.Response;
import com.katadavivienda.encuestas.data.entity.Survey;
import com.katadavivienda.encuestas.data.repository.ResponseRepository;
import com.katadavivienda.encuestas.data.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResponseService {
    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private ResponseRepository responseRepository;

    public boolean submitResponse(String surveyId, ResponseDto responseDto) {
        Survey survey = surveyRepository.findById(surveyId).orElse(null);
        if (survey == null) {
            return false;
        }

        // Validate answers
        for (ResponseDto.AnswerDto answerDto : responseDto.getAnswers()) {
            Survey.Question question = survey.getQuestions().stream()
                    .filter(q -> q.getQuestionId().equals(answerDto.getQuestionId()))
                    .findFirst()
                    .orElse(null);
            if (question == null) {
                return false;
            }
            if (question.isRequired() && answerDto.getAnswer() == null) {
                return false;
            }
            if (answerDto.getAnswer() != null) {
                if (question.getType().equals("age") && !(answerDto.getAnswer() instanceof Integer)) {
                    return false;
                }
                if (question.getType().equals("rating") && !(answerDto.getAnswer() instanceof Integer)) {
                    return false;
                }
                if (question.getType().equals("text") && !(answerDto.getAnswer() instanceof String)) {
                    return false;
                }
                if (question.getType().equals("text") && ((String) answerDto.getAnswer()).length() > 300) {
                    return false;
                }
            }
        }

        Response response = new Response();
        response.setSurveyId(surveyId);
        response.setSubmittedAt(LocalDateTime.now());
        Response.Respondent respondent = new Response.Respondent();
        respondent.setEmail(responseDto.getRespondent().getEmail());
        respondent.setFirstname(responseDto.getRespondent().getFirstname());
        respondent.setLastname(responseDto.getRespondent().getLastname());
        response.setRespondent(respondent);
        response.setAnswers(responseDto.getAnswers().stream().map(a -> {
            Response.Answer answer = new Response.Answer();
            answer.setQuestionId(a.getQuestionId());
            answer.setAnswer(a.getAnswer());
            return answer;
        }).collect(Collectors.toList()));
        responseRepository.save(response);
        return true;
    }

    public List<ResponseDto> getResponsesBySurveyId(String surveyId) {
        return responseRepository.findBySurveyId(surveyId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public void deleteAll() {
        responseRepository.deleteAll();
    }


    private ResponseDto mapToDto(Response response) {
        ResponseDto dto = new ResponseDto();
        ResponseDto.RespondentDto respondentDto = new ResponseDto.RespondentDto();
        respondentDto.setEmail(response.getRespondent().getEmail());
        respondentDto.setFirstname(response.getRespondent().getFirstname());
        respondentDto.setLastname(response.getRespondent().getLastname());
        dto.setRespondent(respondentDto);
        dto.setAnswers(response.getAnswers().stream().map(a -> {
            ResponseDto.AnswerDto answerDto = new ResponseDto.AnswerDto();
            answerDto.setQuestionId(a.getQuestionId());
            answerDto.setAnswer(a.getAnswer());
            return answerDto;
        }).collect(Collectors.toList()));
        return dto;
    }
}
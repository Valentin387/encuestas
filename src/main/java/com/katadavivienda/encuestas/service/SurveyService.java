package com.katadavivienda.encuestas.service;

import com.katadavivienda.encuestas.data.dto.SurveyDto;
import com.katadavivienda.encuestas.data.entity.Survey;
import com.katadavivienda.encuestas.data.repository.SurveyRepository;
import com.fasterxml.uuid.Generators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SurveyService {
    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private ResponseRepository responseRepository;

    public SurveyDto createSurvey(String userId, SurveyDto surveyDto) {
        Survey survey = new Survey();
        survey.setUserId(userId);
        survey.setCompany(surveyDto.getCompany());
        survey.setTitle(surveyDto.getTitle());
        survey.setDescription(surveyDto.getDescription());
        survey.setCreatedAt(LocalDateTime.now());
        survey.setQuestions(surveyDto.getQuestions().stream().map(q -> {
            Survey.Question question = new Survey.Question();
            question.setQuestionId(Generators.timeBasedGenerator().generate().toString());
            question.setQuestionText(q.getQuestionText());
            question.setType(q.getType());
            question.setRequired(q.isRequired());
            return question;
        }).collect(Collectors.toList()));
        Survey savedSurvey = surveyRepository.save(survey);
        return mapToDto(savedSurvey);
    }

    public List<SurveyDto> getSurveysByCompany(String company) {
        return surveyRepository.findByCompany(company).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<SurveyDto> getSurveysByUserId(String userId) {
        return surveyRepository.findByUserId(userId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public SurveyDto getSurveyById(String id) {
        return surveyRepository.findById(id)
                .map(this::mapToDto)
                .orElse(null);
    }

    public boolean deleteSurvey(String id) {
        if (surveyRepository.existsById(id)) {
            surveyRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean deleteSurveyWithResponses(String id) {
        if (surveyRepository.existsById(id)) {
            surveyRepository.deleteById(id);
            responseRepository.deleteBySurveyId(id);
            return true;
        }
        return false;
    }

    public void deleteAll() {
        surveyRepository.deleteAll();
    }

    private SurveyDto mapToDto(Survey survey) {
        SurveyDto dto = new SurveyDto();
        dto.setCompany(survey.getCompany());
        dto.setTitle(survey.getTitle());
        dto.setDescription(survey.getDescription());
        dto.setQuestions(survey.getQuestions().stream().map(q -> {
            SurveyDto.QuestionDto questionDto = new SurveyDto.QuestionDto();
            questionDto.setQuestionText(q.getQuestionText());
            questionDto.setType(q.getType());
            questionDto.setRequired(q.isRequired());
            return questionDto;
        }).collect(Collectors.toList()));
        return dto;
    }
}
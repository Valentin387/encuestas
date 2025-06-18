package com.katadavivienda.encuestas.controller;

import com.katadavivienda.encuestas.data.dto.SurveyDto;
import com.katadavivienda.encuestas.service.SurveyService;
import com.katadavivienda.encuestas.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/surveys")
public class SurveyController {
    @Autowired
    private SurveyService surveyService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SurveyDto> createSurvey(@RequestBody SurveyDto surveyDto,
                                                  @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String userId = jwtUtil.getUserIdFromToken(token);
        SurveyDto createdSurvey = surveyService.createSurvey(userId, surveyDto);
        return ResponseEntity.ok(createdSurvey);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SurveyDto>> getSurveys(@RequestParam(required = false) String company,
                                                      @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String userId = jwtUtil.getUserIdFromToken(token);
        List<SurveyDto> surveys = company != null
                ? surveyService.getSurveysByCompany(company)
                : surveyService.getSurveysByUserId(userId);
        return ResponseEntity.ok(surveys);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SurveyDto> getSurveyById(@PathVariable String id) {
        SurveyDto survey = surveyService.getSurveyById(id);
        return survey != null ? ResponseEntity.ok(survey) : ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Boolean>> deleteSurvey(@PathVariable String id) {
        boolean deleted = surveyService.deleteSurvey(id);
        return ResponseEntity.ok(Map.of("success", deleted));
    }

    @DeleteMapping(value = "/{id}/cascade", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Boolean>> deleteSurveyWithResponses(@PathVariable String id) {
        boolean deleted = surveyService.deleteSurveyWithResponses(id);
        return ResponseEntity.ok(Map.of("success", deleted));
    }
}
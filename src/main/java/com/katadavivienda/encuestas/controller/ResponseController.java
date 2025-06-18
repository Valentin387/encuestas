package com.katadavivienda.encuestas.controller;

import com.katadavivienda.encuestas.data.dto.ResponseDto;
import com.katadavivienda.encuestas.data.dto.SurveyDto;
import com.katadavivienda.encuestas.service.ResponseService;
import com.katadavivienda.encuestas.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/public/surveys")
public class ResponseController {
    @Autowired
    private ResponseService responseService;

    @Autowired
    private SurveyService surveyService;

    @PostMapping(value = "/{id}/response", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Boolean>> submitResponse(@PathVariable String id,
                                                               @RequestBody ResponseDto responseDto) {
        boolean success = responseService.submitResponse(id, responseDto);
        return ResponseEntity.ok(Map.of("success", success));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SurveyDto> getSurveyById(@PathVariable String id) {
        SurveyDto survey = surveyService.getSurveyById(id);
        return survey != null ? ResponseEntity.ok(survey) : ResponseEntity.notFound().build();
    }
}
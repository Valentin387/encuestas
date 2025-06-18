package com.katadavivienda.encuestas.controller;

import com.katadavivienda.encuestas.service.ResponseService;
import com.katadavivienda.encuestas.service.SurveyService;
import com.katadavivienda.encuestas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private ResponseService responseService;

    @DeleteMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Boolean>> deleteAllUsers() {
        userService.deleteAll();
        return ResponseEntity.ok(Map.of("success", true));
    }

    @DeleteMapping(value = "/surveys", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Boolean>> deleteAllSurveys() {
        surveyService.deleteAll();
        return ResponseEntity.ok(Map.of("success", true));
    }

    @DeleteMapping(value = "/responses", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Boolean>> deleteAllResponses() {
        responseService.deleteAll();
        return ResponseEntity.ok(Map.of("success", true));
    }
}
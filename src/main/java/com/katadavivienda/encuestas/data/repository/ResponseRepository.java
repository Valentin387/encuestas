package com.katadavivienda.encuestas.data.repository;

import com.katadavivienda.encuestas.data.entity.Response;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ResponseRepository extends MongoRepository<Response, String> {
    void deleteBySurveyId(String surveyId);
    List<Response> findBySurveyId(String surveyId);
}
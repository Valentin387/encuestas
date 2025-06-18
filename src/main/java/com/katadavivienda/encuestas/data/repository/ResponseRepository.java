package com.katadavivienda.encuestas.data.repository;

import com.katadavivienda.encuestas.data.entity.Response;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResponseRepository extends MongoRepository<Response, String> {
    void deleteBySurveyId(String surveyId);
}
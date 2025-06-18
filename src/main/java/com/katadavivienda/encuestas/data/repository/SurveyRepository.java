package com.katadavivienda.encuestas.data.repository;

import com.katadavivienda.encuestas.data.entity.Survey;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SurveyRepository extends MongoRepository<Survey, String> {
    List<Survey> findByCompany(String company);
    List<Survey> findByUserId(String userId);
}
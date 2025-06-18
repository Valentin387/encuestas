package com.katadavivienda.encuestas.controller;

import com.katadavivienda.encuestas.data.dto.ResponseDto;
import com.katadavivienda.encuestas.service.ResponseService;
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

    @PostMapping(value = "/{id}/response", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Boolean>> submitResponse(@PathVariable String id,
                                                               @RequestBody ResponseDto responseDto) {
        boolean success = responseService.submitResponse(id, responseDto);
        return ResponseEntity.ok(Map.of("success", success));
    }
}
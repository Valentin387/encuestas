package com.katadavivienda.encuestas.controller;

import com.katadavivienda.encuestas.data.dto.TokenDto;
import com.katadavivienda.encuestas.data.dto.UserDto;
import com.katadavivienda.encuestas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenDto> register(@RequestBody UserDto userDto) {
        String token = userService.register(userDto);
        return token != null ? ResponseEntity.ok(new TokenDto(token)) : ResponseEntity.badRequest().build();
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenDto> login(@RequestBody UserDto userDto) {
        String token = userService.login(userDto);
        return token != null ? ResponseEntity.ok(new TokenDto(token)) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping(value = "/reset", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Boolean>> resetUsers() {
        userService.deleteAll();
        return ResponseEntity.ok(Map.of("success", true));
    }
}
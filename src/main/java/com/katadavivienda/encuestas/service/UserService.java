package com.katadavivienda.encuestas.service;

import com.katadavivienda.encuestas.data.dto.UserDto;
import com.katadavivienda.encuestas.data.entity.User;
import com.katadavivienda.encuestas.data.repository.UserRepository;
import com.katadavivienda.encuestas.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String register(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            return null;
        }
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setCompany(userDto.getCompany());
        User savedUser = userRepository.save(user);
        return jwtUtil.generateToken(savedUser.getId(), userDto.getEmail());
    }

    public String login(UserDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail()).orElse(null);
        if (user != null && passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            return jwtUtil.generateToken(user.getId(), userDto.getEmail());
        }
        return null;
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }
}
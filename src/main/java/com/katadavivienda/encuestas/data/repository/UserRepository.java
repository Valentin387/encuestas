package com.katadavivienda.encuestas.data.repository;

import com.katadavivienda.encuestas.data.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
}
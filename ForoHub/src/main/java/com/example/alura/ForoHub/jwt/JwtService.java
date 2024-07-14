package com.example.alura.ForoHub.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class JwtService {

    @Autowired
    private Environment environment;

    public String genToken(String username){
        Algorithm algorithm = Algorithm.HMAC256(Objects.requireNonNull(environment.getProperty("SECRET_KEY_TOKEN")));
        return JWT.create()
                .withIssuer("API del foro de Alura")
                .withSubject(username)
                .sign(algorithm);
    }
    public String validarObtenerSubject(String token){
        Algorithm algorithm = Algorithm.HMAC256(Objects.requireNonNull(environment.getProperty("SECRET_KEY_TOKEN")));
        return JWT.require(algorithm)
                .withIssuer("API del foro de Alura")
                .build()
                .verify(token)
                .getSubject();
    }
}
package com.jwt.security.infra;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.jwt.security.domain.user.User;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;

@Service
public class TokenService {
	@Value("${api.security.token.secret}")
	private String secret;
	
	public String generateToken(User user) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			String token = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getLogin())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
			
			 // Log para verificar se o token está sendo gerado corretamente
	        System.out.println("Token generated: " + token);
            return token;
		} catch(JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);	
		}
	}
	
	 public String validateToken(String token){
	        try {
	            Algorithm algorithm = Algorithm.HMAC256(secret);
	            return JWT.require(algorithm)
	                    .withIssuer("auth-api")
	                    .build()
	                    .verify(token)
	                    .getSubject();
	        } catch (JWTVerificationException exception){
	        	throw new RuntimeException("Invalid or expired token", exception);
	        }
	    }	 

    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}

package com.lenah.GestaoProjetos.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.lenah.GestaoProjetos.model.Usuario;

@Service
public class TokenService {
	
	@Value("$api.security.token.secret")
	private String secret;
	

	public String generateToken(Usuario usuario) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			String token = JWT.create()
					.withIssuer("auth-api")
					.withSubject(usuario.getUsername())
					.withExpiresAt(getExpirationDate())
					.sign(algorithm);
			return token;
		} catch(JWTCreationException e) {
			throw new RuntimeException("Erro na geração do token ", e);
		}
	}
	
	public String validateToken(String token) {
		try {
			
		
		Algorithm algorithm = Algorithm.HMAC256(secret);
		return JWT.require(algorithm)
				.withIssuer("auth-api")
				.build()
				.verify(token)
				.getSubject();
		} catch(JWTCreationException e) {
			return "";
		}
	}
	
	private Instant getExpirationDate() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
}

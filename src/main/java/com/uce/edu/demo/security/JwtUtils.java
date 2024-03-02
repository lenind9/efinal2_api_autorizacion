package com.uce.edu.demo.security;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {
	
	private static final Logger LOG = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${app.jwtSemilla}")
	private String jwtSemilla;

	@Value("${app.jwtExpirationMs}")
	private int jwtExpirationMs;

	public String buildTokenJWT(String nombre) {
		return Jwts.builder().setSubject(nombre).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + this.jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512,
						"ASaskdjhakdhkahdksadhasdasd6a5ds45sad5ad45as4da4d231as5d46sasddasaa54d6as5d4a65d4a65sd4a65d46a5d456ad46ad4a65d456")
				.compact();
	}
	
	public String generateTokenJWT(String nombre, String semilla, int tiempo) {
        LOG.info("Semilla: " + semilla + " Tiempo: " + tiempo);
        return Jwts.builder().setSubject(nombre).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + tiempo))
                .signWith(SignatureAlgorithm.HS512, semilla).compact();
    }
	
}

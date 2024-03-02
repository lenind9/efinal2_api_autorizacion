package com.uce.edu.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uce.edu.demo.security.JwtUtils;
import com.uce.edu.demo.service.to.UsuarioTo;

@RestController
@RequestMapping("/autorizaciones")
@CrossOrigin
public class AuthorizationControllerRestful {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtils jwt;
	
	@GetMapping(path = "/jwt", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String obtenerToken(@RequestBody UsuarioTo usuario) {
		// Autenticacion
		// Validar que el usuario y el password sean correctos
		
		// si la autenticacion es correcta, puedo retornar un token
		this.autenticacion(usuario.getNombre(), usuario.getPassword());
		return this.jwt.buildTokenJWT(usuario.getNombre());
	}
	
	@PostMapping(path = "/generar/{semilla}/{tiempo}", consumes = MediaType.APPLICATION_JSON_VALUE)
    private String generarToken(@RequestBody UsuarioTo usuarioTO, @PathVariable String semilla, @PathVariable Integer tiempo) {
        this.autenticacion(usuarioTO.getNombre(), usuarioTO.getPassword());
        return this.jwt.generateTokenJWT(usuarioTO.getNombre(), semilla, tiempo);
    }

	private void autenticacion(String usuario, String password) {
		UsernamePasswordAuthenticationToken usuarioToken = new UsernamePasswordAuthenticationToken(usuario, password);
		Authentication authentication = this.authenticationManager.authenticate(usuarioToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}

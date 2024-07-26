package com.lenah.GestaoProjetos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lenah.GestaoProjetos.model.AuthenticationDTO;
import com.lenah.GestaoProjetos.model.LoginResponseDTO;
import com.lenah.GestaoProjetos.model.RegisterDTO;
import com.lenah.GestaoProjetos.model.Usuario;
import com.lenah.GestaoProjetos.repository.UsuarioRepository;
import com.lenah.GestaoProjetos.security.TokenService;



@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/login")
	public ResponseEntity Login(@RequestParam String login, @RequestParam String senha) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(login, senha);
		var auth = this.authenticationManager.authenticate(usernamePassword);
		
		var token = tokenService.generateToken((Usuario) auth.getPrincipal());
		
		return ResponseEntity.ok(new LoginResponseDTO(token));
	}
	
	@PostMapping("/register")
	public ResponseEntity Register(@RequestBody RegisterDTO data) {
		if(this.usuarioRepository.findByLogin(data.login())!=null) {
			return ResponseEntity.badRequest().build();
		}
		
		String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
		Usuario newUsuario = new Usuario(null,data.login(),encryptedPassword,data.role());
		
		this.usuarioRepository.save(newUsuario);
		return ResponseEntity.ok().build();
		
	}
}

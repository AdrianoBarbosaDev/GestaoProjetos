package com.lenah.GestaoProjetos.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;


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
	public ResponseEntity<String> login(@RequestParam String login, @RequestParam String senha, RedirectAttributes redirectAttributes, Model model) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(login, senha);
		var auth = this.authenticationManager.authenticate(usernamePassword);

		if (auth.isAuthenticated()) {
			var token = tokenService.generateToken((Usuario) auth.getPrincipal());
			redirectAttributes.addFlashAttribute("msg", "Login bem-sucedido");

			HttpHeaders headers = new HttpHeaders();
			headers.add("Location", "/home");

			return new ResponseEntity<>(headers, HttpStatus.FOUND);
		} else {
			model.addAttribute("erro", "Credenciais inválidas.");
			return new ResponseEntity<>("redirect:/", HttpStatus.NOT_FOUND);
		}
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

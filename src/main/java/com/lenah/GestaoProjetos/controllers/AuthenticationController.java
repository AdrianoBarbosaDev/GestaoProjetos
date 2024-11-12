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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.lenah.GestaoProjetos.model.AuthenticationDTO;
import com.lenah.GestaoProjetos.model.LoginResponseDTO;
import com.lenah.GestaoProjetos.model.RegisterDTO;
import com.lenah.GestaoProjetos.model.Usuario;
import com.lenah.GestaoProjetos.repository.UsuarioRepository;
import com.lenah.GestaoProjetos.security.TokenService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/auth")
public class AuthenticationController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/login")
	@ResponseBody
	public ResponseEntity<Map<String, String>> login(
			@RequestParam String login,
			@RequestParam String senha,
			RedirectAttributes redirectAttributes) {

		Map<String, String> response = new HashMap<>();

		try {
			var usernamePassword = new UsernamePasswordAuthenticationToken(login, senha);
			var auth = this.authenticationManager.authenticate(usernamePassword);

			if (auth.isAuthenticated()) {
				var token = tokenService.generateToken((Usuario) auth.getPrincipal());
				response.put("status", "success");
				response.put("message", "Login bem-sucedido");
				response.put("redirectUrl", "/home"); // URL de redirecionamento em caso de sucesso

				return ResponseEntity.ok(response);
			} else {
				response.put("status", "error");
				response.put("message", "Credenciais inválidas");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
		} catch (Exception e) {
			response.put("status", "error");
			response.put("message", "Erro de autenticação");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
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

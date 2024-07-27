package com.lenah.GestaoProjetos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lenah.GestaoProjetos.model.Usuario;
import com.lenah.GestaoProjetos.repository.UsuarioRepository;


@Controller
@RequestMapping("/recuperarSenha")
public class RecuperarSenhaController {
	
	@Autowired
	UsuarioRepository repo;

	@GetMapping
	public String recuperarSenha(Model model) {
		return "recuperarSenha";
	}
	
	@PostMapping
	public String definirSenha(@RequestParam String senhaNova, @RequestParam String confirmarSenha, RedirectAttributes redirectAttributes, Model model) {
		
		if(!senhaNova.equals(confirmarSenha)) {
			model.addAttribute("erro","As senhas não coincidem.");
			return "recuperarSenha";
		}
		
		//Bloco de Teste, posteriormente será substituido pelo model obtido do usuário via email
		//Atualmente busca o usuário da Eunice
		List<Usuario> listaTeste = repo.findAll();
		Usuario newUser = listaTeste.get(0);
		System.out.println(newUser);
		
		//Criptografa a senha novamente
		String encryptedPassword = new BCryptPasswordEncoder().encode(senhaNova);
		newUser.setSenha(encryptedPassword);
		
		repo.save(newUser);
		
		redirectAttributes.addFlashAttribute("msg", "Senha alterada com sucesso");
		return "redirect:/";
		
	}
}

package com.lenah.GestaoProjetos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lenah.GestaoProjetos.model.Usuario;
import com.lenah.GestaoProjetos.model.UserRole;
import com.lenah.GestaoProjetos.repository.UsuarioRepository;
import com.lenah.GestaoProjetos.services.UsuarioService;


@RestController
@RequestMapping("/getUsuarios")
public class GestaoProjetosController {
	
	@Autowired
	UsuarioRepository repo;

	@GetMapping
	public List<Usuario> getUsuarios() {
		List<Usuario>ListaTeste = repo.findAll();
		System.out.println(ListaTeste);
		return repo.findAll();
	}

}

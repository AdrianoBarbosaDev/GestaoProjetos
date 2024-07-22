package com.lenah.GestaoProjetos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lenah.GestaoProjetos.model.Usuario;
import com.lenah.GestaoProjetos.model.UserRole;
import com.lenah.GestaoProjetos.repository.UsuarioRepository;
import com.lenah.GestaoProjetos.services.UsuarioService;


@RestController
@RequestMapping("/controller")
public class GestaoProjetosController {
	
	@Autowired
	UsuarioRepository repo;

	@GetMapping
	public String getUsuarios() {
		return repo.findAll().toString();
	}
	
}

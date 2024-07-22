package com.lenah.GestaoProjetos.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/controller")
public class GestaoProjetosController {
	
	@GetMapping
	public void teste() {
		System.out.println("Teste funcional");
	}
	
}

package com.lenah.GestaoProjetos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lenah.GestaoProjetos.repository.UsuarioRepository;

@Service
public class UsuarioService{

	@Autowired
	UsuarioRepository repository;
}

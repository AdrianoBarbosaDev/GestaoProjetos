package com.lenah.GestaoProjetos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "sexo")
	private String sexo;
	
	@Column(name = "senha")
	private String senha;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "role")
	private UsuarioRole role;

	public Usuario() {}
	
	public Usuario(Long id, String nome, String email, String sexo) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.sexo = sexo;
	}
	
	

}

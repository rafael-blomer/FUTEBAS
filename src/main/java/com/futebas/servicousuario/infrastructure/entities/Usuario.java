package com.futebas.servicousuario.infrastructure.entities;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Usuario {

	@Id
	private String id;
	private String nome;
	private String email;
	private String senha;
}

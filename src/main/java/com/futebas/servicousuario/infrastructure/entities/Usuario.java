package com.futebas.servicousuario.infrastructure.entities;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class Usuario {

	@Id
	private String id;
	private String nome;
	private String email;
	private String senha;
}

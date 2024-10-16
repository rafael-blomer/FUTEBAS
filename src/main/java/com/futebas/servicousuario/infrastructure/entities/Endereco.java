package com.futebas.servicousuario.infrastructure.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {

	@Id
	private String id;
	private String cep, uf, cidade, bairro, rua;
	private Short numero;
}

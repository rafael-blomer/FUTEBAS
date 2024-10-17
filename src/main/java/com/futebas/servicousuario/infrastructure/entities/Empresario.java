package com.futebas.servicousuario.infrastructure.entities;

import java.io.Serializable;
import java.util.List;

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
public class Empresario extends Usuario implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private List<Campo> campos;
	private String cnpj;
}

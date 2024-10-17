package com.futebas.servicousuario.infrastructure.entities;

import java.io.Serializable;
import java.time.LocalTime;

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
public class Campo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	private Double metroQuadrado, valorPorHora;
	private LocalTime horaAbrir, horaFechar;
	private Boolean campoCoberto;
	private Endereco endereco;
	
}

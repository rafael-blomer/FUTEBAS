package com.futebas.servicousuario.infrastructure.entities;

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
public class Campo{

	@Id
	private String id;
	private Double metroQuadrado, valorPorHora;
	private LocalTime horaAbrir, horaFechar;
	private Boolean campoCoberto;
	private Endereco endereco;
	
}

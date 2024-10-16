package com.futebas.servicousuario.infrastructure.entities;

import java.time.LocalTime;
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
public class Campo extends Usuario{

	private String cnpj;
	private Double metroQuadrado, valorPorHora;
	private LocalTime horaAbrir, horaFechar;
	private List<Endereco> enderecos;
}

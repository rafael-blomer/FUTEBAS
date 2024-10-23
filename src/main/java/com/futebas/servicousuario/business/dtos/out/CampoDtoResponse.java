package com.futebas.servicousuario.business.dtos.out;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CampoDtoResponse {

	private String nomeEmpresa;
	private Double metroQuadrado, valorPorHora;
	private LocalTime horaAbrir, horaFechar;
	private Boolean campoCoberto;
	private EnderecoDtoResponse endereco;
}

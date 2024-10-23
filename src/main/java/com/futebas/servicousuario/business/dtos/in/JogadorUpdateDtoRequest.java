package com.futebas.servicousuario.business.dtos.in;

import com.futebas.servicousuario.infrastructure.enums.QualidadeEnum;

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
public class JogadorUpdateDtoRequest {
	
	private String nome;
	private Boolean jogaDeGoleiro;
	private QualidadeEnum qualidade;
}

package com.futebas.servicousuario.business.dtos.out;

import java.time.LocalDate;

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
public class JogadorDtoResponse {
	private String nome, email;
	private Boolean jogaDeGoleiro;
	private QualidadeEnum qualidade;
	private LocalDate dataNascimento;
}

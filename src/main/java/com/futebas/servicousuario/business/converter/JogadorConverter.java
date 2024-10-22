package com.futebas.servicousuario.business.converter;

import org.springframework.stereotype.Component;

import com.futebas.servicousuario.business.dtos.out.JogadorDtoResponse;
import com.futebas.servicousuario.infrastructure.entities.Jogador;

@Component
public class JogadorConverter {
	
	public JogadorDtoResponse paraJogadorDto(Jogador jogador) {
		return JogadorDtoResponse.builder()
				.nome(jogador.getNome())
				.email(jogador.getEmail())
				.dataNascimento(jogador.getDataNascimento())
				.qualidade(jogador.getQualidade())
				.jogaDeGoleiro(jogador.getJogaDeGoleiro())
				.build();
	}

}

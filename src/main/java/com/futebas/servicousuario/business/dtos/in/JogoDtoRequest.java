package com.futebas.servicousuario.business.dtos.in;

import java.time.LocalDateTime;

import com.futebas.servicousuario.infrastructure.entities.Campo;
import com.futebas.servicousuario.infrastructure.entities.Jogador;

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
public class JogoDtoRequest {

	private Jogador criador;
	private LocalDateTime dataHora;
	private Boolean abertoParaJogadores;
	private Campo campo;
	private Integer numeroMaximoJogadores;
}

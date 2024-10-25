package com.futebas.servicousuario.business.dtos.out;

import java.time.LocalDateTime;
import java.util.List;

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
public class JogoDtoResponse {

	private Jogador criador;
	private LocalDateTime dataHora;
	private List<Jogador> jogadores;
	private Boolean abertoParaJogadores;
	private Campo campo;
	private Integer numeroMaximoJogadores;
}

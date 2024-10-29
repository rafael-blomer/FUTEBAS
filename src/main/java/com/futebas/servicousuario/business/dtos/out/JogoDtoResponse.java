package com.futebas.servicousuario.business.dtos.out;

import java.time.LocalDateTime;
import java.util.List;

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

	private LocalDateTime dataHora;
	private Boolean abertoParaJogadores;
	private JogadorDtoResponse criador;
	private List<JogadorDtoResponse> jogadores;
	private String nomeCampo;
	private Integer numeroMaximoJogadores;
}

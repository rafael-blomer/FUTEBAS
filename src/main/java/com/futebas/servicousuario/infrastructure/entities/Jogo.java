package com.futebas.servicousuario.infrastructure.entities;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Jogo {

	@Id
	private String id;
	private Jogador criador;
	private LocalDateTime dataHora;
	private List<Jogador> jogadores;
	private Boolean abertoParaJogadores;
	private Campo campo;
	private Integer numeroMaximoJogadores;
}

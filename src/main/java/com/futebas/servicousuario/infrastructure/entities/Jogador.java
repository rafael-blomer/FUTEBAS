package com.futebas.servicousuario.infrastructure.entities;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;

import com.futebas.servicousuario.infrastructure.enums.QualidadeEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Jogador extends Usuario{

	private String cpf;
	private Boolean jogaDeGoleiro;
	private QualidadeEnum qualidade;
	private LocalDate dataNascimento;
}

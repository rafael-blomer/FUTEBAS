package com.futebas.servicousuario.infrastructure.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

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
public class Jogador extends Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	private String cpf;
	private Boolean jogaDeGoleiro;
	private QualidadeEnum qualidade;
	private LocalDate dataNascimento;

	@Override
	public int hashCode() {
		return Objects.hash(cpf);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jogador other = (Jogador) obj;
		return Objects.equals(cpf, other.cpf);
	}

}

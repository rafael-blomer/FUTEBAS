package com.futebas.servicousuario.infrastructure.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Empresario extends Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<Campo> campos;
	private String cnpj;

	@Override
	public int hashCode() {
		return Objects.hash(cnpj);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empresario other = (Empresario) obj;
		return Objects.equals(cnpj, other.cnpj);
	}

}

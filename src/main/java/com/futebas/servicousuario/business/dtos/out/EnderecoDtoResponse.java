package com.futebas.servicousuario.business.dtos.out;

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
public class EnderecoDtoResponse {
	private String cep, uf, cidade, bairro, rua;
	private Short numero;
}

package com.futebas.servicousuario.business.dtos.in;

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
public class EmpresarioCadastroDtoRequest {
	private String nome, email, cnpj, senha;
}

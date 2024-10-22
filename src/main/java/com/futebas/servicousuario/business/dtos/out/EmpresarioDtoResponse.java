package com.futebas.servicousuario.business.dtos.out;

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
public class EmpresarioDtoResponse {
	private String nome, email, cnpj;
	private List<CampoDtoResponse> campos;
}

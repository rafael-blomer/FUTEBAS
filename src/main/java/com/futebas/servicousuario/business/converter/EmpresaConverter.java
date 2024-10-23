package com.futebas.servicousuario.business.converter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.futebas.servicousuario.business.dtos.out.CampoDtoResponse;
import com.futebas.servicousuario.business.dtos.out.EmpresarioDtoResponse;
import com.futebas.servicousuario.business.dtos.out.EnderecoDtoResponse;
import com.futebas.servicousuario.infrastructure.entities.Campo;
import com.futebas.servicousuario.infrastructure.entities.Empresario;
import com.futebas.servicousuario.infrastructure.entities.Endereco;

@Component
public class EmpresaConverter {

	public EmpresarioDtoResponse paraEmpresarioDto(Empresario empresario) {
		return EmpresarioDtoResponse.builder().nome(empresario.getNome()).email(empresario.getEmail())
				.cnpj(empresario.getCnpj()).campos(forListCampoDto(empresario.getCampos(), empresario.getNome()))
				.build();
	}

	private List<CampoDtoResponse> forListCampoDto(List<Campo> list, String nomeEmpresa) {
		return list.stream().map(campo -> paraCampoDto(campo, nomeEmpresa)).toList();
	}

	public CampoDtoResponse paraCampoDto(Campo campo, String nomeEmpresa) {
		return CampoDtoResponse.builder().nomeEmpresa(nomeEmpresa).metroQuadrado(campo.getMetroQuadrado())
				.valorPorHora(campo.getValorPorHora()).horaAbrir(campo.getHoraAbrir()).horaFechar(campo.getHoraFechar())
				.campoCoberto(campo.getCampoCoberto()).endereco(paraEnderecoDto(campo.getEndereco())).build();
	}

	private EnderecoDtoResponse paraEnderecoDto(Endereco endereco) {
		return EnderecoDtoResponse.builder().rua(endereco.getRua()).bairro(endereco.getBairro())
				.cidade(endereco.getCidade()).uf(endereco.getUf()).cep(endereco.getCep()).numero(endereco.getNumero())
				.build();
	}
}

package com.futebas.servicousuario.business.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.futebas.servicousuario.business.dtos.in.EmpresarioCadastroDtoRequest;
import com.futebas.servicousuario.business.dtos.in.JogoDtoRequest;
import com.futebas.servicousuario.business.dtos.out.CampoDtoResponse;
import com.futebas.servicousuario.business.dtos.out.EmpresarioDtoResponse;
import com.futebas.servicousuario.business.dtos.out.EnderecoDtoResponse;
import com.futebas.servicousuario.business.dtos.out.JogadorDtoResponse;
import com.futebas.servicousuario.business.dtos.out.JogoDtoResponse;
import com.futebas.servicousuario.infrastructure.entities.Campo;
import com.futebas.servicousuario.infrastructure.entities.Empresario;
import com.futebas.servicousuario.infrastructure.entities.Endereco;
import com.futebas.servicousuario.infrastructure.entities.Jogador;
import com.futebas.servicousuario.infrastructure.entities.Jogo;

@Component
public class Converter {
	
	public JogadorDtoResponse paraJogadorDto(Jogador jogador) {
		return JogadorDtoResponse.builder()
				.nome(jogador.getNome())
				.email(jogador.getEmail())
				.dataNascimento(jogador.getDataNascimento())
				.qualidade(jogador.getQualidade())
				.jogaDeGoleiro(jogador.getJogaDeGoleiro())
				.build();
	}

	public EmpresarioDtoResponse paraEmpresarioDto(Empresario empresario) {
		return EmpresarioDtoResponse.builder()
				.nome(empresario.getNome())
				.email(empresario.getEmail())
				.cnpj(empresario.getCnpj())
				.campos(forListCampoDto(empresario.getCampos(), empresario.getNome()))
				.build();
	}

	private List<CampoDtoResponse> forListCampoDto(List<Campo> list, String nomeEmpresa) {
		return list.stream().map(campo -> paraCampoDto(campo, nomeEmpresa)).toList();
	}

	public CampoDtoResponse paraCampoDto(Campo campo, String nomeEmpresa) {
		return CampoDtoResponse.builder()
				.nomeEmpresa(nomeEmpresa)
				.metroQuadrado(campo.getMetroQuadrado())
				.valorPorHora(campo.getValorPorHora())
				.horaAbrir(campo.getHoraAbrir())
				.horaFechar(campo.getHoraFechar())
				.campoCoberto(campo.getCampoCoberto())
				.endereco(paraEnderecoDto(campo.getEndereco()))
				.build();
	}
	
	public Campo paraCampoEntity(CampoDtoResponse dto) {
		return Campo.builder()
				.campoCoberto(dto.getCampoCoberto())
				.endereco(paraEnderecoEntity(dto.getEndereco()))
				.horaAbrir(dto.getHoraAbrir())
				.horaFechar(dto.getHoraFechar())
				.metroQuadrado(dto.getMetroQuadrado())
				.valorPorHora(dto.getValorPorHora())
				.build();
	}
	
	private Endereco paraEnderecoEntity(EnderecoDtoResponse dto) {
		return Endereco.builder()
				.bairro(dto.getBairro())
				.cep(dto.getCep())
				.cidade(dto.getCidade())
				.numero(dto.getNumero())
				.rua(dto.getRua())
				.uf(dto.getUf())
				.build();
	}

	private EnderecoDtoResponse paraEnderecoDto(Endereco endereco) {
		return EnderecoDtoResponse.builder()
				.rua(endereco.getRua())
				.bairro(endereco.getBairro())
				.cidade(endereco.getCidade())
				.uf(endereco.getUf())
				.cep(endereco.getCep())
				.numero(endereco.getNumero())
				.build();
	}
	
	public Jogo requestParaJogoEntity(JogoDtoRequest dto) {
		return Jogo.builder()
				.dataHora(dto.getDataHora())
				.abertoParaJogadores(dto.getAbertoParaJogadores())
				.jogadores(new ArrayList<Jogador>())
				.campo(dto.getCampo())
				.numeroMaximoJogadores(dto.getNumeroMaximoJogadores())
				.build();
	}
	
	public JogoDtoResponse paraJogoDtoResponse(Jogo jogo, Empresario empresario) {
	    String nomeEmpresario = (empresario != null) ? empresario.getNome() : "Empresário não encontrado";
		return JogoDtoResponse.builder()
				.dataHora(jogo.getDataHora())
				.abertoParaJogadores(jogo.getAbertoParaJogadores())
				.numeroMaximoJogadores(jogo.getNumeroMaximoJogadores())
				.criador(paraJogadorDto(jogo.getCriador()))
				.jogadores(jogo.getJogadores().stream().map(this::paraJogadorDto).collect(Collectors.toList()))
				.nomeCampo(nomeEmpresario)
				.build();
	}

	public Empresario paraEmpresarioEntity(EmpresarioCadastroDtoRequest empresarioDto) {
		return Empresario.builder()
				.nome(empresarioDto.getNome())
				.email(empresarioDto.getEmail())
				.senha(empresarioDto.getSenha())
				.cnpj(empresarioDto.getCnpj())
				.campos(new ArrayList<Campo>())
				.build();
	}
}

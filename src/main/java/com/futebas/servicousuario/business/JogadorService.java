package com.futebas.servicousuario.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.futebas.servicousuario.business.converter.EmpresaConverter;
import com.futebas.servicousuario.business.converter.JogadorConverter;
import com.futebas.servicousuario.business.dtos.out.CampoDtoResponse;
import com.futebas.servicousuario.business.dtos.out.JogadorDtoResponse;
import com.futebas.servicousuario.business.exceptions.ObjectNotFoundException;
import com.futebas.servicousuario.infrastructure.entities.Campo;
import com.futebas.servicousuario.infrastructure.entities.Jogador;
import com.futebas.servicousuario.infrastructure.repositories.CampoRepository;
import com.futebas.servicousuario.infrastructure.repositories.JogadorRepository;
import com.futebas.servicousuario.infrastructure.security.JwtUtil;

@Service
public class JogadorService {
	
	@Autowired
	private JogadorRepository repo;
	@Autowired
	private CampoRepository campoRepo;
	@Autowired
	private JwtUtil jwt;
	@Autowired
	private JogadorConverter converter;
	@Autowired
	private EmpresaConverter converterEmp;
	
	private Jogador getByEmail(String token) {
		String email = jwt.extrairEmailToken(token.substring(7));
		Jogador obj = repo.findByEmail(email);
		if (obj == null)
			throw new ObjectNotFoundException("Jogador não encotrado.");
		return obj;
	}
	
	public JogadorDtoResponse getJogadorDtoByEmail(String token) {
		Jogador obj = getByEmail(token);
		return converter.paraJogadorDto(obj);
	}
	
	public void deleteJogador(String token){
		Jogador obj = getByEmail(token);
		repo.delete(obj);
	}
	
	public JogadorDtoResponse updateJogador(String token, Jogador novo) {
		Jogador antigo = getByEmail(token);
		updateData(antigo, novo);
		return converter.paraJogadorDto(repo.save(antigo));
	}

	private void updateData(Jogador antigo, Jogador novo) {
		antigo.setNome(novo.getNome() != null ? novo.getNome() : antigo.getNome());
		antigo.setQualidade(novo.getQualidade() != null ? novo.getQualidade() : antigo.getQualidade());
		antigo.setJogaDeGoleiro(novo.getJogaDeGoleiro() != null ? novo.getJogaDeGoleiro() : antigo.getJogaDeGoleiro());
	}
	
	public List<CampoDtoResponse> listarCamposBairro(String bairro) {
		List<Campo> list = campoRepo.findByEnderecoBairro(bairro);
		List<CampoDtoResponse> listDto = new ArrayList<CampoDtoResponse>();
		for (Campo i : list) 
			listDto.add(converterEmp.paraCampoDto(i));
		return listDto;
	}
	
	
	public List<CampoDtoResponse> listarCamposCidade(String cidade) {
		List<Campo> list = campoRepo.findByEnderecoCidade(cidade);
		List<CampoDtoResponse> listDto = new ArrayList<CampoDtoResponse>();
		for (Campo i : list) 
			listDto.add(converterEmp.paraCampoDto(i));
		return listDto;
	}
}

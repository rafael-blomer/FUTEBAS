package com.futebas.servicousuario.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public Jogador getByEmail(String token) {
		String email = jwt.extrairEmailToken(token.substring(7));
		Jogador obj = repo.findByEmail(email);
		if (obj == null)
			throw new ObjectNotFoundException("Jogador não encotrado.");
		return obj;
	}
	
	public void deleteJogador(String token){
		Jogador obj = getByEmail(token);
		repo.delete(obj);
	}
	
	public Jogador updateJogador(String token, Jogador novo) {
		Jogador antigo = getByEmail(token);
		updateData(antigo, novo);
		return repo.save(antigo);
	}

	private void updateData(Jogador antigo, Jogador novo) {
		antigo.setNome(novo.getNome());
		antigo.setQualidade(novo.getQualidade());
		antigo.setJogaDeGoleiro(novo.getJogaDeGoleiro());
	}
	
	public List<Campo> listarCamposBairro(String bairro) {
		return campoRepo.findByEnderecoBairro(bairro);
	}
	
	
	public List<Campo> listarCamposCidade(String cidade) {
		return campoRepo.findByEnderecoCidade(cidade);
	}
}

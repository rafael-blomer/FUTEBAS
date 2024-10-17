package com.futebas.servicousuario.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	@Transactional
	public void deleteJogador(String token){
		String email = jwt.extrairEmailToken(token.substring(7));
		Jogador obj = getByEmail(email);
		repo.delete(obj);
	}
	
	@Transactional
	public Jogador updateJogador(String token, Jogador novo) {
		String email = jwt.extrairEmailToken(token.substring(7));
		Jogador antigo = getByEmail(email);
		updateData(antigo, novo);
		return repo.save(antigo);
	}

	private void updateData(Jogador antigo, Jogador novo) {
		antigo.setNome(novo.getNome());
		antigo.setCpf(novo.getCpf());
		antigo.setQualidade(novo.getQualidade());
		antigo.setJogaDeGoleiro(novo.getJogaDeGoleiro());
	}
	
	public List<Campo> listarCampos() {
		return campoRepo.findAll();
	}
	
	public List<Campo> listarCamposBairro(String bairro) {
		return campoRepo.findByEnderecoBairro(bairro);
	}
}

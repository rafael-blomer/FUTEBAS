package com.futebas.servicousuario.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.futebas.servicousuario.business.exceptions.ObjectNotFoundException;
import com.futebas.servicousuario.infrastructure.entities.Jogador;
import com.futebas.servicousuario.infrastructure.repositories.JogadorRepository;

@Service
public class JogadorService {
	
	@Autowired
	private JogadorRepository repo;

	public Jogador getByEmail(String email) {
		Jogador obj = repo.findByEmail(email);
		if (obj == null)
			throw new ObjectNotFoundException("Jogador não encotrado.");
		return obj;
	}
	
	public void deleteJogador(String email){
		Jogador obj = getByEmail(email);
		repo.delete(obj);
	}
	
	public Jogador updateJogador(String email, Jogador novo) {
		Jogador antigo = getByEmail(email);
		updateData(antigo, novo);
		return repo.save(antigo);
	}

	private void updateData(Jogador antigo, Jogador novo) {
		antigo.setNome(novo.getNome());
		antigo.setQualidade(novo.getQualidade());
		antigo.setJogaDeGoleiro(novo.getJogaDeGoleiro());
	}
}

package com.futebas.servicousuario.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.futebas.servicousuario.business.exceptions.DataIntegratyException;
import com.futebas.servicousuario.infrastructure.entities.Empresario;
import com.futebas.servicousuario.infrastructure.entities.Jogador;
import com.futebas.servicousuario.infrastructure.repositories.EmpresarioRepository;
import com.futebas.servicousuario.infrastructure.repositories.JogadorRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private JogadorRepository jogadorRepo;
	@Autowired
	private EmpresarioRepository empresarioRepo;

	public Empresario cadastroEmpresario(Empresario empresario) {
		if(findCampoByCnpj(empresario.getCnpj()) != null)
			throw new DataIntegratyException("CNPJ já cadastrado.");
		if(findCampoByEmail(empresario.getEmail()) != null)
			throw new DataIntegratyException("Email já cadastrado.");
		return empresarioRepo.save(empresario);
	}
	
	public Jogador cadastroJogador(Jogador jogador) {
		if(findJogadorByCpf(jogador.getCpf()) != null)
			throw new DataIntegratyException("CPF já cadastrado.");
		if(findJogadorByEmail(jogador.getEmail()) != null)
			throw new DataIntegratyException("Email já cadastrado.");
		return jogadorRepo.save(jogador);
	}
	
	public Jogador findJogadorByCpf(String cpf) {
		return jogadorRepo.findByCpf(cpf);
	}
	
	public Empresario findCampoByCnpj(String cnpj) {
		return empresarioRepo.findByCnpj(cnpj);
	}
	
	public Empresario findCampoByEmail(String email) {
		return empresarioRepo.findByEmail(email);
	}
	
	public Jogador findJogadorByEmail(String email) {
		return jogadorRepo.findByEmail(email);
	}
}

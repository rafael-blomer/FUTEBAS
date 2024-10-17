package com.futebas.servicousuario.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	@Autowired
	private PasswordEncoder encoder;

	public Empresario cadastroEmpresario(Empresario empresario) {
		if(findEmpresaByCnpj(empresario.getCnpj()) != null)
			throw new DataIntegratyException("CNPJ já cadastrado.");
		if(findEmpresaByEmail(empresario.getEmail()) != null)
			throw new DataIntegratyException("Email já cadastrado.");
		empresario.setSenha(encoder.encode(empresario.getSenha()));
		return empresarioRepo.save(empresario);
	}
	
	public Jogador cadastroJogador(Jogador jogador) {
		if(findJogadorByCpf(jogador.getCpf()) != null)
			throw new DataIntegratyException("CPF já cadastrado.");
		if(findJogadorByEmail(jogador.getEmail()) != null)
			throw new DataIntegratyException("Email já cadastrado.");
		jogador.setSenha(encoder.encode(jogador.getSenha()));
		return jogadorRepo.save(jogador);
	}
	
	private Jogador findJogadorByCpf(String cpf) {
		return jogadorRepo.findByCpf(cpf);
	}
	
	private Empresario findEmpresaByCnpj(String cnpj) {
		return empresarioRepo.findByCnpj(cnpj);
	}
	
	private Empresario findEmpresaByEmail(String email) {
		return empresarioRepo.findByEmail(email);
	}
	
	private Jogador findJogadorByEmail(String email) {
		return jogadorRepo.findByEmail(email);
	}
}

package com.futebas.servicousuario.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.futebas.servicousuario.business.dtos.in.LoginDtoRequest;
import com.futebas.servicousuario.business.exceptions.DataIntegratyException;
import com.futebas.servicousuario.infrastructure.entities.Empresario;
import com.futebas.servicousuario.infrastructure.entities.Jogador;
import com.futebas.servicousuario.infrastructure.repositories.EmpresarioRepository;
import com.futebas.servicousuario.infrastructure.repositories.JogadorRepository;
import com.futebas.servicousuario.infrastructure.security.JwtUtil;

@Service
public class UsuarioService {

	@Autowired
	private JogadorRepository jogadorRepo;
	@Autowired
	private EmpresarioRepository empresarioRepo;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private AuthenticationManager authenticationManager;

	public Empresario cadastroEmpresario(Empresario empresario) {
		if (findEmpresaByCnpj(empresario.getCnpj()) != null)
			throw new DataIntegratyException("CNPJ já cadastrado.");
		if (findEmpresaByEmail(empresario.getEmail()) != null)
			throw new DataIntegratyException("Email já cadastrado.");
		empresario.setSenha(encoder.encode(empresario.getSenha()));
		return empresarioRepo.save(empresario);
	}

	public Jogador cadastroJogador(Jogador jogador) {
		if (findJogadorByCpf(jogador.getCpf()) != null)
			throw new DataIntegratyException("CPF já cadastrado.");
		if (findJogadorByEmail(jogador.getEmail()) != null)
			throw new DataIntegratyException("Email já cadastrado.");
		jogador.setSenha(encoder.encode(jogador.getSenha()));
		return jogadorRepo.save(jogador);
	}

	public String login(LoginDtoRequest dto) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String role = findRoleByEmail(dto.getEmail());
		if (role == null) 
			throw new DataIntegratyException("Usuário não encontrado.");
		String jwtToken = jwtUtil.generateToken(authentication.getName(), role);
		return "Bearer " + jwtToken;
	}

	private String findRoleByEmail(String email) {
		Jogador jogador = findJogadorByEmail(email);
		if (jogador != null) 
			return "JOGADOR";
		Empresario empresario = findEmpresaByEmail(email);
		if (empresario != null) 
			return "EMPRESARIO";
		return null;
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

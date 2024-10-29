package com.futebas.servicousuario.business;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.futebas.servicousuario.business.converter.Converter;
import com.futebas.servicousuario.business.dtos.in.JogadorUpdateDtoRequest;
import com.futebas.servicousuario.business.dtos.out.CampoDtoResponse;
import com.futebas.servicousuario.business.dtos.out.JogadorDtoResponse;
import com.futebas.servicousuario.business.exceptions.ObjectNotFoundException;
import com.futebas.servicousuario.infrastructure.entities.Empresario;
import com.futebas.servicousuario.infrastructure.entities.Jogador;
import com.futebas.servicousuario.infrastructure.repositories.EmpresarioRepository;
import com.futebas.servicousuario.infrastructure.repositories.JogadorRepository;
import com.futebas.servicousuario.infrastructure.security.JwtUtil;

@Service
public class JogadorService {
	
	@Autowired
	private JogadorRepository repo;
	@Autowired
	private EmpresarioRepository empRepo;
	@Autowired
	private JwtUtil jwt;
	@Autowired
	private Converter converter;
	
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
	
	public JogadorDtoResponse updateJogador(String token, JogadorUpdateDtoRequest novo) {
		Jogador antigo = getByEmail(token);
		updateData(antigo, novo);
		return converter.paraJogadorDto(repo.save(antigo));
	}

	private void updateData(Jogador antigo, JogadorUpdateDtoRequest novo) {
		antigo.setNome(novo.getNome() != null ? novo.getNome() : antigo.getNome());
		antigo.setQualidade(novo.getQualidade() != null ? novo.getQualidade() : antigo.getQualidade());
		antigo.setJogaDeGoleiro(novo.getJogaDeGoleiro() != null ? novo.getJogaDeGoleiro() : antigo.getJogaDeGoleiro());
	}
	
	public List<CampoDtoResponse> listarCamposBairro(String bairro, String cidade) {
	    List<Empresario> empresarios = empRepo.findEmpresariosByCidade(cidade);
	    return empresarios.stream()
	            .flatMap(empresario -> empresario.getCampos().stream()
	                .filter(campo -> campo.getEndereco().getBairro().equalsIgnoreCase(bairro))
	                .filter(campo -> campo.getEndereco().getCidade().equalsIgnoreCase(cidade))
	                .map(campo -> converter.paraCampoDto(campo, empresario.getNome()))) 
	            .collect(Collectors.toList());
	}


	public List<CampoDtoResponse> listarCamposCidade(String cidade) {
	    List<Empresario> empresarios = empRepo.findEmpresariosByCidade(cidade);
	    return empresarios.stream()
	            .flatMap(empresario -> empresario.getCampos().stream()
	                .filter(campo -> campo.getEndereco().getCidade().equalsIgnoreCase(cidade))
	                .map(campo -> converter.paraCampoDto(campo, empresario.getNome()))) 
	            .collect(Collectors.toList());
	}

}

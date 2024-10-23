package com.futebas.servicousuario.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.futebas.servicousuario.business.converter.EmpresaConverter;
import com.futebas.servicousuario.business.dtos.in.EmpresarioUpdateDtoRequest;
import com.futebas.servicousuario.business.dtos.out.CampoDtoResponse;
import com.futebas.servicousuario.business.dtos.out.EmpresarioDtoResponse;
import com.futebas.servicousuario.business.exceptions.ObjectNotFoundException;
import com.futebas.servicousuario.infrastructure.entities.Campo;
import com.futebas.servicousuario.infrastructure.entities.Empresario;
import com.futebas.servicousuario.infrastructure.repositories.EmpresarioRepository;
import com.futebas.servicousuario.infrastructure.security.JwtUtil;

@Service
public class EmpresarioService {

	@Autowired
	private EmpresarioRepository repo;
	@Autowired
	private JwtUtil jwt;
	@Autowired
	private EmpresaConverter converter;

	private Empresario getByEmail(String token) {
		String email = jwt.extrairEmailToken(token.substring(7));
		Empresario obj = repo.findByEmail(email);
		if (obj == null)
			throw new ObjectNotFoundException("Empresario não encotrado.");
		return obj;
	}
	
	public EmpresarioDtoResponse getEmpresarioDtoByEmail(String token) {
		Empresario obj = getByEmail(token);
		return converter.paraEmpresarioDto(obj);
	}

	@Transactional
	public void deleteEmpresario(String token) {
		Empresario obj = getByEmail(token);
		repo.delete(obj);
	}

	@Transactional
	public EmpresarioDtoResponse updateEmpresario(String token, EmpresarioUpdateDtoRequest novo) {
		Empresario antigo = getByEmail(token);
		updateData(antigo, novo);
		return converter.paraEmpresarioDto(repo.save(antigo));
	}

	private void updateData(Empresario antigo, EmpresarioUpdateDtoRequest novo) {
		antigo.setNome(novo.getNome() != null ? novo.getNome() : antigo.getNome());
	}

	public CampoDtoResponse adicionarCampo(String token, Campo campo) {
		Empresario obj = getByEmail(token);
		obj.getCampos().add(campo);
		repo.save(obj);
		return converter.paraCampoDto(campo, obj.getNome());
	}

	@Transactional
	public void removerCampo(String token, Campo campo) {
		Empresario obj = getByEmail(token);
		obj.getCampos().remove(campo);
		repo.save(obj);
	}

	public List<CampoDtoResponse> getTodosCamposEmp(String token) {
		Empresario obj = getByEmail(token);
		List<Campo> list = obj.getCampos(); 
		List<CampoDtoResponse> listDto = new ArrayList<CampoDtoResponse>();
		for (Campo i : list) 
			listDto.add(converter.paraCampoDto(i, obj.getNome()));
		return listDto;
	}
}

package com.futebas.servicousuario.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	public Empresario getByEmail(String token) {
		String email = jwt.extrairEmailToken(token.substring(7));
		Empresario obj = repo.findByEmail(email);
		if (obj == null)
			throw new ObjectNotFoundException("Empresario não encotrado.");
		return obj;
	}
	
	@Transactional
	public void deleteEmpresario(String token){
		String email = jwt.extrairEmailToken(token.substring(7));
		Empresario obj = getByEmail(email);
		repo.delete(obj);
	}
	
	@Transactional
	public Empresario updateEmpresario(String token, Empresario novo) {
		String email = jwt.extrairEmailToken(token.substring(7));
		Empresario antigo = getByEmail(email);
		updateData(antigo, novo);
		return repo.save(antigo);
	}

	private void updateData(Empresario antigo, Empresario novo) {
		antigo.setNome(novo.getNome());
		antigo.setEmail(novo.getEmail());
	}
	
	public Campo adicionarCampo(String token, Campo campo) {
		String email = jwt.extrairEmailToken(token.substring(7));
		Empresario obj = getByEmail(email);
		obj.getCampos().add(campo);
		return campo;
	}
	
	@Transactional
	public void deleteCampo(String token, Campo campo) {
		String email = jwt.extrairEmailToken(token.substring(7));
		Empresario obj = getByEmail(email);
		obj.getCampos().remove(campo);
	}
	
	public List<Campo> buscarTodosCamposEmp(String token) {
		String email = jwt.extrairEmailToken(token.substring(7));
		Empresario obj = getByEmail(email);
		return obj.getCampos();
	}
}

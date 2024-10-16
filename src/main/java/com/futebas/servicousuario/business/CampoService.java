package com.futebas.servicousuario.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.futebas.servicousuario.business.exceptions.ObjectNotFoundException;
import com.futebas.servicousuario.infrastructure.entities.Campo;
import com.futebas.servicousuario.infrastructure.repositories.CampoRepository;

@Service
public class CampoService {

	@Autowired
	private CampoRepository repo;

	public Campo getByEmail(String email) {
		Campo obj = repo.findByEmail(email);
		if (obj == null)
			throw new ObjectNotFoundException("Campo não encotrado.");
		return obj;
	}
	
	public void deleteCampo(String email){
		Campo obj = getByEmail(email);
		repo.delete(obj);
	}
	
	public Campo updateCampo(String email, Campo novo) {
		Campo antigo = getByEmail(email);
		updateData(antigo, novo);
		return repo.save(antigo);
	}

	private void updateData(Campo antigo, Campo novo) {
		antigo.setValorPorHora(novo.getValorPorHora());
		antigo.setHoraAbrir(novo.getHoraAbrir());
		antigo.setHoraFechar(novo.getHoraFechar());
		antigo.setMetroQuadrado(novo.getMetroQuadrado());
	}
}

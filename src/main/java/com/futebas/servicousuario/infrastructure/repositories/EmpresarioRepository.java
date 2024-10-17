package com.futebas.servicousuario.infrastructure.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.futebas.servicousuario.infrastructure.entities.Empresario;

@Repository
public interface EmpresarioRepository extends MongoRepository<Empresario, String> {

	Empresario findByCnpj(String cnpj);
	
	Empresario findByEmail(String email);
}

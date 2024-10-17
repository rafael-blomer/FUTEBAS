package com.futebas.servicousuario.infrastructure.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.futebas.servicousuario.infrastructure.entities.Campo;

@Repository
public interface CampoRepository extends MongoRepository<Campo, String> {

	List<Campo> findByEnderecoBairro(String bairro);
}

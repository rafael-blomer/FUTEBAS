package com.futebas.servicousuario.infrastructure.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.futebas.servicousuario.infrastructure.entities.Endereco;

@Repository
public interface EnderecoRepository extends MongoRepository<Endereco, String> {

}

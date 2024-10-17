package com.futebas.servicousuario.infrastructure.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.futebas.servicousuario.infrastructure.entities.Jogador;

@Repository
public interface JogadorRepository extends MongoRepository<Jogador, String> {

	Jogador findByCpf(String cpf);
	
	Jogador findByEmail(String email);
	
	Optional<Jogador> findOptByEmail(String email);

}

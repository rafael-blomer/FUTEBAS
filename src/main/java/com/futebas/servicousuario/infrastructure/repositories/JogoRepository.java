package com.futebas.servicousuario.infrastructure.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.futebas.servicousuario.infrastructure.entities.Jogo;

@Repository
public interface JogoRepository extends MongoRepository<Jogo, String> {

	List<Jogo> findByDataHoraBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
}

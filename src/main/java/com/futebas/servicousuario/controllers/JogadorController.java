package com.futebas.servicousuario.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.futebas.servicousuario.business.JogadorService;
import com.futebas.servicousuario.infrastructure.entities.Campo;
import com.futebas.servicousuario.infrastructure.entities.Jogador;

@RestController
@RequestMapping("/jogador")
public class JogadorController {
	
	@Autowired
	private JogadorService jogadorService;
	
	
	@GetMapping
	public ResponseEntity<Jogador> getByEmail(@RequestHeader("Authorization") String token) {
		return ResponseEntity.ok().body(jogadorService.getByEmail(token));
	}
	
	@DeleteMapping
	public ResponseEntity<Void> deleteJogadorByEmail(@RequestHeader("Authorization") String token) {
		jogadorService.deleteJogador(token);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping
	public ResponseEntity<Jogador> updateJogador(@RequestHeader("Authorization") String token, @RequestBody Jogador jogador) {
		return ResponseEntity.ok().body(jogadorService.updateJogador(token, jogador));
	}
	
	@GetMapping("/cidade")
	public ResponseEntity<List<Campo>> listaCaposCidade(@RequestParam String cidade) {
		return ResponseEntity.ok().body(jogadorService.listarCamposCidade(cidade));
	}
	
	@GetMapping("/bairro")
	public ResponseEntity<List<Campo>> listaCaposBairro(@RequestParam String bairro) {
		return ResponseEntity.ok().body(jogadorService.listarCamposBairro(bairro));
	}
}

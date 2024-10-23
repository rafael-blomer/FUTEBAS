package com.futebas.servicousuario.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.futebas.servicousuario.business.JogadorService;
import com.futebas.servicousuario.business.dtos.in.JogadorUpdateDtoRequest;
import com.futebas.servicousuario.business.dtos.out.CampoDtoResponse;
import com.futebas.servicousuario.business.dtos.out.JogadorDtoResponse;

@RestController
@RequestMapping("/jogador")
public class JogadorController {
	
	@Autowired
	private JogadorService jogadorService;
	
	@PreAuthorize("hasRole('JOGADOR')")
	@GetMapping
	public ResponseEntity<JogadorDtoResponse> getByEmail(@RequestHeader("Authorization") String token) {
		return ResponseEntity.ok().body(jogadorService.getJogadorDtoByEmail(token));
	}
	
	@PreAuthorize("hasRole('JOGADOR')")
	@DeleteMapping
	public ResponseEntity<Void> deleteJogadorByEmail(@RequestHeader("Authorization") String token) {
		jogadorService.deleteJogador(token);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasRole('JOGADOR')")
	@PutMapping
	public ResponseEntity<JogadorDtoResponse> updateJogador(@RequestHeader("Authorization") String token, @RequestBody JogadorUpdateDtoRequest jogador) {
		return ResponseEntity.ok().body(jogadorService.updateJogador(token, jogador));
	}
	
	@PreAuthorize("hasRole('JOGADOR')")
	@GetMapping("/cidade")
	public ResponseEntity<List<CampoDtoResponse>> listaCamposCidade(@RequestParam String cidade) {
		return ResponseEntity.ok().body(jogadorService.listarCamposCidade(cidade));
	}
	
	@PreAuthorize("hasRole('JOGADOR')")
	@GetMapping("/bairro")
	public ResponseEntity<List<CampoDtoResponse>> listaCamposBairro(@RequestParam String bairro, @RequestParam String cidade) {
		return ResponseEntity.ok().body(jogadorService.listarCamposBairro(bairro, cidade));
	}
}

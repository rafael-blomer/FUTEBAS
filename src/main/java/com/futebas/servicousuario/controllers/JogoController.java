package com.futebas.servicousuario.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.futebas.servicousuario.business.JogoService;
import com.futebas.servicousuario.infrastructure.entities.Jogo;

@RestController
@RequestMapping("/jogos")
public class JogoController {
	
	@Autowired
	private JogoService service;
	
	@PreAuthorize("hasAnyRole('JOGADOR', 'EMPRESARIO')")
	@PostMapping
	public ResponseEntity<Jogo> criarJogo(@RequestBody Jogo jogo, @RequestHeader("Authorization") String token) {
		return ResponseEntity.ok().body(service.criarJogo(jogo, token));
	}
	
	@PreAuthorize("hasAnyRole('JOGADOR', 'EMPRESARIO')")
	@PutMapping
	public ResponseEntity<Jogo> atualizarJogo(@RequestBody Jogo jogo, @RequestHeader("Authorization") String token, @RequestParam String id) {
		return ResponseEntity.ok().body(service.jogoUpdate(jogo, token, id));
	}
	
	@PreAuthorize("hasAnyRole('JOGADOR', 'EMPRESARIO')")
	@GetMapping
	public ResponseEntity<Jogo> buscarDadosJogo(@RequestParam String id) {
		return ResponseEntity.ok().body(service.buscarDadosJogo(id));
	}
	
	@PreAuthorize("hasAnyRole('JOGADOR', 'EMPRESARIO')")
	@DeleteMapping
	public ResponseEntity<Void> cancelarJogo(@RequestParam String id, @RequestHeader("Authorization") String token) {
		service.cancelarJogo(id, token);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('JOGADOR', 'EMPRESARIO')")
	@PutMapping("/abrir-inscricao")
	public ResponseEntity<Jogo> abrirInscricaoJogo(@RequestParam String id, @RequestHeader("Authorization") String token) {
		return ResponseEntity.ok().body(service.abrirInscricaoJogo(id, token));
	}
	
	@PreAuthorize("hasAnyRole('JOGADOR', 'EMPRESARIO')")
	@PutMapping("/fechar-inscricao")
	public ResponseEntity<Jogo> fecharInscricaoJogo(@RequestParam String id, @RequestHeader("Authorization") String token) {
		return ResponseEntity.ok().body(service.fecharInscricaoJogo(id, token));
	}
	
	@PreAuthorize("hasAnyRole('JOGADOR', 'EMPRESARIO')")
	@GetMapping("/abertos")
	public ResponseEntity<List<Jogo>> buscarJogosAbertos() {
		return ResponseEntity.ok().body(service.jogosAbertos());
	}
	
	@PreAuthorize("hasAnyRole('JOGADOR', 'EMPRESARIO')")
	@GetMapping("/abertos-dia")
	public ResponseEntity<List<Jogo>> buscarJogosAbertosDia(@RequestParam LocalDate data) {
		return ResponseEntity.ok().body(service.jogosAbertosDia(data));
	}
	
	@PreAuthorize("hasRole('EMPRESARIO')")
	@GetMapping("/empresa")
	public ResponseEntity<List<Jogo>> buscarJogosAbertosDia(@RequestHeader("Authorization") String token) {
		return ResponseEntity.ok().body(service.jogosMarcadosPorEmpresa(token));
	}

	@PreAuthorize("hasAnyRole('JOGADOR', 'EMPRESARIO')")
	@PostMapping("/adicionar")
	public ResponseEntity<Jogo> adicionarJogador(@RequestParam String cpfJogador,@RequestParam String id, @RequestHeader("Authorization") String token) {
		return ResponseEntity.ok().body(service.adicionarJogador(cpfJogador, id, token));
	}
	
	@PreAuthorize("hasAnyRole('JOGADOR', 'EMPRESARIO')")
	@DeleteMapping("/remover")
	public ResponseEntity<Jogo> removerJogador(@RequestParam String cpfJogador,@RequestParam String id, @RequestHeader("Authorization") String token) {
		return ResponseEntity.ok().body(service.removerJogador(cpfJogador, id, token));
	}

}

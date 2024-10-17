package com.futebas.servicousuario.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.futebas.servicousuario.business.UsuarioService;
import com.futebas.servicousuario.business.dtos.in.LoginDtoRequest;
import com.futebas.servicousuario.infrastructure.entities.Empresario;
import com.futebas.servicousuario.infrastructure.entities.Jogador;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginDtoRequest dto) {
		return ResponseEntity.ok(usuarioService.login(dto));
	}

	@PostMapping("/jogador")
	public ResponseEntity<Jogador> cadastroJogador(@RequestBody Jogador jogador) {
		Jogador obj = usuarioService.cadastroJogador(jogador);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping("/empresa")
	public ResponseEntity<Empresario> cadastroEmpresa(@RequestBody Empresario empresa) {
		Empresario obj = usuarioService.cadastroEmpresario(empresa);
		return ResponseEntity.ok().body(obj);
	}
}

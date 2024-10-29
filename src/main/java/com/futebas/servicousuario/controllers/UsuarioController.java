package com.futebas.servicousuario.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.futebas.servicousuario.business.UsuarioService;
import com.futebas.servicousuario.business.dtos.in.EmpresarioCadastroDtoRequest;
import com.futebas.servicousuario.business.dtos.in.LoginDtoRequest;
import com.futebas.servicousuario.business.dtos.out.EmpresarioDtoResponse;
import com.futebas.servicousuario.business.dtos.out.JogadorDtoResponse;
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
	public ResponseEntity<JogadorDtoResponse> cadastroJogador(@RequestBody Jogador jogador) {
		return ResponseEntity.ok().body(usuarioService.cadastroJogador(jogador));
	}

	@PostMapping("/empresa")
	public ResponseEntity<EmpresarioDtoResponse> cadastroEmpresa(@RequestBody EmpresarioCadastroDtoRequest empresarioDto) {
		return ResponseEntity.ok().body(usuarioService.cadastroEmpresario(empresarioDto));
	}
}

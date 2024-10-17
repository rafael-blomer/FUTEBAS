package com.futebas.servicousuario.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.futebas.servicousuario.business.UsuarioService;
import com.futebas.servicousuario.business.dtos.in.LoginDtoRequest;
import com.futebas.servicousuario.infrastructure.entities.Empresario;
import com.futebas.servicousuario.infrastructure.entities.Jogador;
import com.futebas.servicousuario.infrastructure.security.JwtUtil;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/login")
	public String login(@RequestBody Empresario dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(),
                        dto.getSenha())
        );
        return "Bearer " + jwtUtil.generateToken(authentication.getName());
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

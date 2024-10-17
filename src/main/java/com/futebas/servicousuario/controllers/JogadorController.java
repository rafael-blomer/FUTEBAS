package com.futebas.servicousuario.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.futebas.servicousuario.business.JogadorService;

@RestController
@RequestMapping("/jogador")
public class JogadorController {
	
	@Autowired
	private JogadorService jogadorService;
	
}

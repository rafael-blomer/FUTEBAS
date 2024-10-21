package com.futebas.servicousuario.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.futebas.servicousuario.business.EmpresarioService;

@RestController
@RequestMapping("/empresa")
public class EmpresarioController {
	
	@Autowired
	private EmpresarioService service;

	
}

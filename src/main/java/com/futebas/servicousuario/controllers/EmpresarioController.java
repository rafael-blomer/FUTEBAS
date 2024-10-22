package com.futebas.servicousuario.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.futebas.servicousuario.business.EmpresarioService;
import com.futebas.servicousuario.business.dtos.in.EmpresarioUpdateDtoRequest;
import com.futebas.servicousuario.business.dtos.out.CampoDtoResponse;
import com.futebas.servicousuario.business.dtos.out.EmpresarioDtoResponse;
import com.futebas.servicousuario.infrastructure.entities.Campo;

@RestController
@RequestMapping("/empresario")
public class EmpresarioController {
	
	@Autowired
	private EmpresarioService service;

	@GetMapping
	public ResponseEntity<EmpresarioDtoResponse> getByEmail(@RequestHeader("Authorization") String token) {
		return ResponseEntity.ok().body(service.getEmpresarioDtoByEmail(token));
	}
	
	@DeleteMapping
	public ResponseEntity<Void> deleteEmpresarioByEmail(@RequestHeader("Authorization") String token) {
		service.deleteEmpresario(token);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping
	public ResponseEntity<EmpresarioDtoResponse> updateEmpresario(@RequestHeader("Authorization") String token, @RequestBody EmpresarioUpdateDtoRequest empresario) {
		return ResponseEntity.ok().body(service.updateEmpresario(token, empresario));
	}
	
	@PostMapping("/campos")
	public ResponseEntity<CampoDtoResponse> adicionarCampoParaEmp(@RequestHeader("Authorization") String token, @RequestBody Campo campo) {
		return ResponseEntity.ok().body(service.adicionarCampo(token, campo));
	}
	
	@DeleteMapping("/campos")
	public ResponseEntity<Void> deleteCampoEmp(@RequestHeader("Authorization") String token, @RequestBody Campo campo) {
		service.removerCampo(token, campo);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/campos")
	public ResponseEntity<List<CampoDtoResponse>> getCamposByEmpresario(@RequestHeader("Authorization") String token) {
		return ResponseEntity.ok().body(service.getTodosCamposEmp(token));
	}
}

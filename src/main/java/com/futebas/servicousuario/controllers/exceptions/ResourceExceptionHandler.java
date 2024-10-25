package com.futebas.servicousuario.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.futebas.servicousuario.business.exceptions.DataIntegratyException;
import com.futebas.servicousuario.business.exceptions.MaxPlayersReachedException;
import com.futebas.servicousuario.business.exceptions.ObjectNotFoundException;
import com.futebas.servicousuario.business.exceptions.PlayerInGameException;
import com.futebas.servicousuario.business.exceptions.TimeNotAllowedException;
import com.futebas.servicousuario.business.exceptions.WithoutPermissionException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(DataIntegratyException.class)
	public ResponseEntity<StandardError> dataIntegratyException(DataIntegratyException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Documento já cadastrado.", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> notFoundException(ObjectNotFoundException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Objeto não encontrado.", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(TimeNotAllowedException.class)
	public ResponseEntity<StandardError> timeGameException(TimeNotAllowedException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.CONFLICT;
		StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Problema com horário de jogo.", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(WithoutPermissionException.class)
	public ResponseEntity<StandardError> noPermissionException(WithoutPermissionException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.FORBIDDEN;
		StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Problema com permissão de jogo.", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(PlayerInGameException.class)
	public ResponseEntity<StandardError> playerInGameException(PlayerInGameException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Jogador já está cadastrado no jogo.", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(MaxPlayersReachedException.class)
	public ResponseEntity<StandardError> maxPlayersException(MaxPlayersReachedException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.CONFLICT;
		StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Quantidade máxima de jogadores atingida.", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
}

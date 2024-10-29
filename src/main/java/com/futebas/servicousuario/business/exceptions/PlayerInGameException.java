package com.futebas.servicousuario.business.exceptions;

public class PlayerInGameException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public PlayerInGameException(String msg) {
		super(msg);
	}

}

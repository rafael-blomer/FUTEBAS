package com.futebas.servicousuario.business.exceptions;

public class MaxPlayersReachedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public MaxPlayersReachedException(String msg) {
		super(msg);
	}
}

package com.futebas.servicousuario.business.exceptions;

public class TimeNotAllowedException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public TimeNotAllowedException(String msg) {
		super(msg);
	}

}

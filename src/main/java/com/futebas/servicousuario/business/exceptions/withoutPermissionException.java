package com.futebas.servicousuario.business.exceptions;

public class withoutPermissionException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public withoutPermissionException(String msg) {
		super(msg);
	}

}

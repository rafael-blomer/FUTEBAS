package com.futebas.servicousuario.business.exceptions;

public class WithoutPermissionException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public WithoutPermissionException(String msg) {
		super(msg);
	}

}

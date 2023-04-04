package com.processamento.cnab.domain.exceptions;

public class ProcessamentoCnabException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ProcessamentoCnabException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProcessamentoCnabException(String message) {
		super(message);
	}

}

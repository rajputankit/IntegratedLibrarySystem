package edu.sjsu.cmpe275.termproject.exceptions;

public class ExistingEmailException extends Exception{

	private String exceptionReason;
	
	public ExistingEmailException(String exceptionReason) {
		this.exceptionReason = exceptionReason;
	}
	
	public String getExceptionReason() {
		return exceptionReason;
	}

	public void setExceptionReason(String exceptionReason) {
		this.exceptionReason = exceptionReason;
	}
}

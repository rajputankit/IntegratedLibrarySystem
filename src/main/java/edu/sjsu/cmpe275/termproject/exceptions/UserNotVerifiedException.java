package edu.sjsu.cmpe275.termproject.exceptions;

public class UserNotVerifiedException extends Exception{

	private String exceptionReason;
	
	public UserNotVerifiedException(String exceptionReason) {
		this.exceptionReason = exceptionReason;
	}
	
	public String getExceptionReason() {
		return exceptionReason;
	}

	public void setExceptionReason(String exceptionReason) {
		this.exceptionReason = exceptionReason;
	}
	
}

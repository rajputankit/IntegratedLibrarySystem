package edu.sjsu.cmpe275.termproject.exceptions;

public class UserNotFoundException extends Exception{

private String exceptionReason;
	
	public UserNotFoundException(String exceptionReason) {
		this.exceptionReason = exceptionReason;
	}
	
	public String getExceptionReason() {
		return exceptionReason;
	}

	public void setExceptionReason(String exceptionReason) {
		this.exceptionReason = exceptionReason;
	}
}

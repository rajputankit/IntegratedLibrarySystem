package edu.sjsu.cmpe275.termproject.exceptions;

public class ExistingSjsuIdException extends Exception{

private String exceptionReason;
	
	public ExistingSjsuIdException(String exceptionReason) {
		this.exceptionReason = exceptionReason;
	}
	
	public String getExceptionReason() {
		return exceptionReason;
	}

	public void setExceptionReason(String exceptionReason) {
		this.exceptionReason = exceptionReason;
	}
}

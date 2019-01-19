package edu.sjsu.cmpe275.termproject.exceptions;

public class BookNotFoundException extends Exception{

	
private String exceptionReason;
	
	public BookNotFoundException(String exceptionReason) {
		this.exceptionReason = exceptionReason;
	}
	
	public String getExceptionReason() {
		return exceptionReason;
	}

	public void setExceptionReason(String exceptionReason) {
		this.exceptionReason = exceptionReason;
	}
}

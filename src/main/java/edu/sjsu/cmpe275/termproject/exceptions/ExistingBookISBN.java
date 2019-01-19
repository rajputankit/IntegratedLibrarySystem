package edu.sjsu.cmpe275.termproject.exceptions;

public class ExistingBookISBN extends Exception{

private String exceptionReason;
	
	public ExistingBookISBN(String exceptionReason) {
		this.exceptionReason = exceptionReason;
	}
	
	public String getExceptionReason() {
		return exceptionReason;
	}

	public void setExceptionReason(String exceptionReason) {
		this.exceptionReason = exceptionReason;
	}
}

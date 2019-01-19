package edu.sjsu.cmpe275.termproject.exceptions;

public class BookWaitlistedException extends Exception{

	
private String exceptionReason;
	
	public BookWaitlistedException(String exceptionReason) {
		this.exceptionReason = exceptionReason;
	}
	
	public String getExceptionReason() {
		return exceptionReason;
	}

	public void setExceptionReason(String exceptionReason) {
		this.exceptionReason = exceptionReason;
	}
}

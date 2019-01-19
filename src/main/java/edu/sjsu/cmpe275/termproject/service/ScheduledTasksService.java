package edu.sjsu.cmpe275.termproject.service;

public interface ScheduledTasksService {
	
	public void getDueBooks();
	public void keepTime() throws Exception;
	public void checkReservedBooks() throws Exception;
	

}

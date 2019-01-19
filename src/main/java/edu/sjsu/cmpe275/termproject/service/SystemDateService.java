package edu.sjsu.cmpe275.termproject.service;

import java.util.Date;

public interface SystemDateService {

	Date getSystemDate();
	void setSystemDate(Date date) throws Exception;
}

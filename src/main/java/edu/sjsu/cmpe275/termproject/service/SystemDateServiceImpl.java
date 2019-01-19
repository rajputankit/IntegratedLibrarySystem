package edu.sjsu.cmpe275.termproject.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.termproject.DAO.DAO;

@Service
public class SystemDateServiceImpl implements SystemDateService {

	@Autowired
	DAO dao;
	@Autowired
	ScheduledTasksService scheduledTasksService;
	
	public Date getSystemDate(){
		
		return dao.getSystemDate();
	}
	
	public void setSystemDate(Date systemDate) throws Exception{
	
		dao.setSystemDate(systemDate);
		
	}
}

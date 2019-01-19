package edu.sjsu.cmpe275.termproject.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import edu.sjsu.cmpe275.termproject.DAO.DAO;
import edu.sjsu.cmpe275.termproject.model.Book;
import edu.sjsu.cmpe275.termproject.model.BookCheckout;
import edu.sjsu.cmpe275.termproject.model.Librarian;
import edu.sjsu.cmpe275.termproject.model.Patron;
import edu.sjsu.cmpe275.termproject.service.BookCheckoutService;
import edu.sjsu.cmpe275.termproject.service.BookService;
import edu.sjsu.cmpe275.termproject.service.PatronService;
import edu.sjsu.cmpe275.termproject.service.ScheduledTasksService;
import edu.sjsu.cmpe275.termproject.service.SystemDateService;

@Controller
public class HomeController {
	

	@Autowired 
	BookService bookService;
	@Autowired
	SystemDateService systemDateService;
	@Autowired
	PatronService patronService;
	@Autowired
	DAO dao;
	@Autowired
	BookCheckoutService bookCheckoutService;
	@Autowired
	ScheduledTasksService scheduledTasksService;
	
	@RequestMapping(value = "/signup")
	public String sendSignUpForm() {
		System.out.println("Sending form");
		return "signup";
	}
	
	@RequestMapping(value = "/logout")
	public String logoutUser(HttpSession session) {
		session.invalidate();
		System.out.println("logging out user");
		return "signup";
	}
	
	@RequestMapping(value="/setdate", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void setDate(HttpSession session, @RequestParam("systemDate") Date date) throws Exception{
		systemDateService.setSystemDate(date);
		System.out.println("The date from frontEnd is: "+date);
		scheduledTasksService.getDueBooks();
		scheduledTasksService.checkReservedBooks();
	}
	

	@RequestMapping(value="/getdate", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<HashMap<String, Date>> getDate(HttpSession session){
		
		HashMap<String, Date> date = new HashMap();
		date.put("systemDate", systemDateService.getSystemDate());
		return new ResponseEntity<HashMap<String, Date>>(date, HttpStatus.OK);
	}
		
	
}
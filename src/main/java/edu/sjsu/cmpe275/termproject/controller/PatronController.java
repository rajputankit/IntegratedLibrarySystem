package edu.sjsu.cmpe275.termproject.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import edu.sjsu.cmpe275.termproject.exceptions.UserNotSignedInException;
import edu.sjsu.cmpe275.termproject.model.BookCheckout;
import edu.sjsu.cmpe275.termproject.model.Patron;
import edu.sjsu.cmpe275.termproject.service.BookCheckoutService;
import edu.sjsu.cmpe275.termproject.service.BookService;
import edu.sjsu.cmpe275.termproject.service.PatronService;

@Controller
@RequestMapping("/patron")
public class PatronController {

	@Autowired
	PatronService patronService;
	@Autowired
	BookService bookService;
	

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signUpForm() throws Exception {
		return "patron";
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public void signUpPatron(@ModelAttribute Patron patron, HttpSession session, Model model) throws Exception {

		System.out.println("CREATING PATRON");
		System.out.println("Patron name:" + patron.getFirstName());

		patronService.signUpPatron(patron);
		session.setAttribute("loggedInUser", patron.getEmail());
		return;
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/verifyuser", method = RequestMethod.GET)
	public String verifyUserForm(HttpSession session) throws Exception {

		System.out.println("I got this! :" + session.getAttribute("loggedInUser"));
		return "userconfirmation";
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/verifyuser", method = RequestMethod.POST)
	public void verifyUserPost(HttpSession session, @RequestParam String code) throws Exception {

		String patronEmail = (String) session.getAttribute("loggedInUser");
		patronService.setPatronVerified(code, patronEmail);
		return;
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public void signInPatron(HttpSession session, @RequestParam String email, @RequestParam String password,
			Model model) throws Exception {


		patronService.signInPatron(email, password);
		session.setAttribute("loggedInUser", email);
		return;
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String patronHome(HttpSession session, Model model) throws Exception {
		if (session.getAttribute("loggedInUser") == null)
			throw new UserNotSignedInException("No user found, please sign in");

		return "welcomepatron";
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/getpatroninfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Patron> getPatronInfo(HttpSession session, Model model) throws Exception {

		if (session.getAttribute("loggedInUser") == null) throw new UserNotSignedInException("No user found, please sign in");

		String email = (String) session.getAttribute("loggedInUser");	
		Patron patron = patronService.getPatronByEmail(email);
		
		Integer totalFine = bookService.getTotalFine(patron.getEmail());
		System.out.println("The total fine"+totalFine);
		patron.setTotalFine(totalFine);
		
		//System.out.println("PATRON WAITLISTS " + patron.getWaitListedBooks().get(0).getTitle());
		
		List<BookCheckout> patronBooks = bookService.getBooksCheckedoutByPatron(patron.getEmail());
		
		for (Iterator iterator = patronBooks.iterator(); iterator.hasNext();) {
			BookCheckout bc = (BookCheckout) iterator.next();	
			System.out.println("TEST checkout bookID " + bc.getCheckoutBook().getBookId() + " title " + bc.getCheckoutBook().getTitle());
		}
				
		return new ResponseEntity<Patron>(patron, HttpStatus.OK);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/renew/{checkoutId}", method = RequestMethod.GET)
	public void renewBook(HttpSession session, @PathVariable("checkoutId") int checkoutId) throws Exception {
		
		bookService.renewBook(session.getAttribute("loggedInUser").toString(), checkoutId);
		
		return;
	}
	

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public void logOutPatron(HttpSession session) throws Exception {

		session.invalidate();
		return;
	}

	
	
	
}










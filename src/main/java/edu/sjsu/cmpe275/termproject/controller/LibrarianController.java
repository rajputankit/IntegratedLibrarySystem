package edu.sjsu.cmpe275.termproject.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import edu.sjsu.cmpe275.termproject.model.Book;
import edu.sjsu.cmpe275.termproject.model.Librarian;
import edu.sjsu.cmpe275.termproject.service.BookService;
import edu.sjsu.cmpe275.termproject.service.LibrarianService;

@Controller
@RequestMapping("/librarian")
public class LibrarianController {

	@Autowired
	LibrarianService librarianService;
	@Autowired
	BookService bookService;

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String sendSignUpForm() throws Exception {

		return "librarian";
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public void signUp(@ModelAttribute Librarian librarian, HttpSession session) throws Exception {

		System.out.println("CREATING Librarian");
		session.setAttribute("loggedInUser", librarian.getSjsuEmail());
		librarianService.signUpLibrarian(librarian);
		
		/*
		 * Librarian l = new Librarian(); l.setSjsuEmail("l@gmail.com");
		 * l.setFirstName("andy"); l.setSjsuId("0110"); l.setSjsuId("l1");
		 * librarianService.signUpLibrarian(l);
		 * 
		 * System.out.println("CREATING PATRON"); Librarian l1 = new
		 * Librarian(); l1.setSjsuEmail("l1@gmail.com");
		 * l1.setFirstName("andy"); l1.setSjsuId("01110"); l1.setSjsuId("12");
		 * librarianService.signUpLibrarian(l1);
		 * 
		 * System.out.println("CREATING PATRON"); Librarian l2 = new
		 * Librarian(); l2.setSjsuEmail("l2@gmail.com");
		 * l2.setFirstName("andy"); l2.setSjsuId("0110"); l2.setSjsuId("l3");
		 * 
		 * librarianService.signUpLibrarian(l2);
		 */
		return;
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public void signIn(@RequestParam String email, @RequestParam String password, HttpSession session, Model model)
			throws Exception {

		System.out.println("The LIBRARIAN is" + email + "  with id " + password);

		session.setAttribute("loggedInUser", email);
		librarianService.signInLibrarian(email, password);		
		return;
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/verifyuser", method = RequestMethod.GET)
	public String verifyUserForm(HttpSession session) throws Exception {

		System.out.println("I got this! :" + session.getAttribute("loggedInUser"));
		return "librarianconfirmation";
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/verifyuser", method = RequestMethod.POST)
	public void verifyUserPost(HttpSession session, @RequestParam String code) throws Exception {

		String librarianEmail = (String) session.getAttribute("loggedInUser");
		librarianService.setLibrarianVerified(code, librarianEmail);
		return;
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String getHomePage(HttpSession session) throws Exception {

		// System.out.println("The user logging in
		// is"+session.getAttribute("loggedInUser").toString());

		/*
		 * if (session.getAttribute("loggedInUser") == null) throw new
		 * UserNotSignedInException("Please Sign in");
		 */

		return "welcomelibrarian";
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public void logOutLibrarian(HttpSession session) throws Exception {

		session.invalidate();

		return;
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/alllibrarians", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List> getAllLibrarians(Model model) throws Exception {

		List<Librarian> librarians = librarianService.getAllLibrarians();
		return new ResponseEntity<List>(librarians, HttpStatus.OK);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/books", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List> librarianBooks(
			@RequestParam(value = "sjsuEmail", required = false) String librarianEmail) throws Exception {

		System.out.println("librarian book email " + librarianEmail);
		List<Book> books = bookService.getBooksCreatedByLibrarian(librarianEmail);
		return new ResponseEntity<List>(books, HttpStatus.OK);
	}
}

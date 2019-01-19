package edu.sjsu.cmpe275.termproject.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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

import edu.sjsu.cmpe275.termproject.exceptions.BookWaitlistedException;
import edu.sjsu.cmpe275.termproject.exceptions.UserNotSignedInException;
import edu.sjsu.cmpe275.termproject.model.Book;
import edu.sjsu.cmpe275.termproject.model.BookCheckout;
import edu.sjsu.cmpe275.termproject.model.Keyword;
import edu.sjsu.cmpe275.termproject.model.Patron;
import edu.sjsu.cmpe275.termproject.service.BookCheckoutService;
import edu.sjsu.cmpe275.termproject.service.BookService;
import edu.sjsu.cmpe275.termproject.service.ISBNService;
import edu.sjsu.cmpe275.termproject.service.KeywordService;

@Controller
@RequestMapping("/book")
public class BookController {

	@Autowired
	BookService bookService;
	@Autowired
	BookCheckoutService bookCheckoutService;
	@Autowired
	ISBNService isbnService;
	@Autowired
	KeywordService keywordService;

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void addBook(@ModelAttribute Book book, HttpSession session) throws Exception {

		String patronEmail = (String) session.getAttribute("loggedInUser");
		
		book.setCurrentStatus(1);
		book.setCurrentNumberOfCopies(book.getNumberOfCopies());
		book.setBookCheckouts(new ArrayList<BookCheckout>());
		book.setKeywordList(new HashSet<Keyword>());
		book.setWaitList(new ArrayList<Patron>());	
		book.setAddedBy(patronEmail);
		book.setUpdatedBy(patronEmail);

		String[] keywords = book.getKeywordArray();

		for (int i = 0; i < keywords.length; i++) {
			Keyword k = new Keyword();
			k.setKeyword(keywords[i]);
			k.setBooks(new HashSet<Book>());	
			k.getBooks().add(book);
			book.getKeywordList().add(k);
		}

		
		//ADDING BOOK DETAILS AS KEYWORDS FOR SEARCH
		List<String> searchCriteria = new ArrayList<String>();	
		searchCriteria.add(book.getAuthor());
		searchCriteria.add(book.getTitle());
		searchCriteria.add(book.getISBN());
		searchCriteria.add(book.getYearOfPublication());
		searchCriteria.add(book.getPublisher());
		for (int i = 0; i < 5; i++) {
			Keyword k = new Keyword();
			k.setKeyword(searchCriteria.get(i));
			k.setBooks(new HashSet<Book>());
			k.getBooks().add(book);
			book.getKeywordList().add(k);
		}

		bookService.addBook(book);
		return;
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/update/{bookId}", method = RequestMethod.GET)
	public String updateBook(@PathVariable("bookId") String bookId, Model model) throws Exception {

		Book book = bookService.getBookById(Integer.parseInt(bookId));
		model.addAttribute("book", book);
		return "update";
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateBookData(@ModelAttribute Book receivedBook, HttpSession session) throws Exception {

		System.out.println("id " + receivedBook.getBookId() + " book data " + receivedBook.getAuthor() + " tit "
				+ receivedBook.getTitle());
		System.out.println("keywords " + Arrays.toString(receivedBook.getKeywordArray()));

		String librarianEmail = (String) session.getAttribute("loggedInUser");
		System.out.println("UPDATING BOOK...updater " + librarianEmail);

		Book existingBook = bookService.getBookById(receivedBook.getBookId());

		existingBook.setISBN(receivedBook.getISBN());
		existingBook.setAuthor(receivedBook.getAuthor());
		existingBook.setTitle(receivedBook.getTitle());
		existingBook.setCallNumber(receivedBook.getCallNumber());
		existingBook.setPublisher(receivedBook.getPublisher());
		existingBook.setYearOfPublication(receivedBook.getYearOfPublication());
		existingBook.setLocation(receivedBook.getLocation());
		existingBook.setNumberOfCopies(receivedBook.getNumberOfCopies());
		existingBook.setUpdatedBy(librarianEmail);
		
		String[] newKeywordsArray = receivedBook.getKeywordArray();
		Set<Keyword> newKeywords = new HashSet<Keyword>();
		for (int i = 0; i < newKeywordsArray.length; i++) {

			Keyword newKeyword = new Keyword();
			newKeyword.setKeyword(newKeywordsArray[i]);			
			newKeyword.setBooks(new HashSet<Book>());
			newKeyword.getBooks().add(existingBook);
			newKeywords.add(newKeyword);	
		}
		
		//ADDING BOOK DETAILS AS KEYWORDS FOR SEARCH
		List<String> searchCriteria = new ArrayList<String>();		
		searchCriteria.add(receivedBook.getAuthor());
		searchCriteria.add(receivedBook.getTitle());
		searchCriteria.add(receivedBook.getISBN());
		searchCriteria.add(receivedBook.getYearOfPublication());
		searchCriteria.add(receivedBook.getPublisher());
		for (int i = 0; i < 5; i++) {
			Keyword k = new Keyword();
			k.setKeyword(searchCriteria.get(i));
			k.setBooks(new HashSet<Book>());
			k.getBooks().add(existingBook);
			newKeywords.add(k);
		}	

		existingBook.setKeywordList(newKeywords);
		System.out.println("update books keywords " + existingBook.getKeywordList().size());
		System.out.println("updating book");
		bookService.updateBook(existingBook);
		return "update";
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void deleteBook(@RequestParam(value = "bookId", required = false) Integer bookId) throws Exception {

		System.out.println("DELETING BOOK " + bookId);

		bookService.deleteBook(bookId);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/checkout", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List> bookCheckout(@RequestParam(value = "bookIds[]", required = false) String[] bookIds,
			@RequestParam(value = "patronEmail", required = false) String patronEmail, HttpSession session)
			throws Exception {

		patronEmail = (String) session.getAttribute("loggedInUser");
		System.out.println("chcekout received " + bookIds);

		Integer[] finalBookIds = new Integer[bookIds.length];

		for (int i = 0; i < bookIds.length; i++) {
			finalBookIds[i] = Integer.parseInt(bookIds[i]);
		}

		System.out.println("received in " + finalBookIds.length);
		System.out.println("received in " + Arrays.toString(finalBookIds));

		List<BookCheckout> tempBookCheckouts = (List<BookCheckout>) bookCheckoutService.bookCheckout(finalBookIds,
				patronEmail);

		System.out.println("new checkouts " + tempBookCheckouts.size());

		return new ResponseEntity<List>(tempBookCheckouts, HttpStatus.OK);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/return", method = RequestMethod.POST)
	public void returnBook(@RequestParam(value = "bookIds[]", required = false) String[] bookIds,
			@RequestParam(value = "patronEmail", required = false) String patronEmail, HttpSession session)
			throws Exception {

		System.out.println("RETURNING BOOK");

		patronEmail = (String) session.getAttribute("loggedInUser");
		System.out.println("return received " + bookIds);

		Integer[] finalBookIds = new Integer[bookIds.length];

		for (int i = 0; i < bookIds.length; i++) {
			finalBookIds[i] = Integer.parseInt(bookIds[i]);
		}

		bookService.returnBook(finalBookIds, patronEmail);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List> searchBook(@RequestParam(value = "search") String search) throws Exception {

		List<Book> books = bookService.searchBook(search);
		System.out.println("RETURNING BOOK");
		return new ResponseEntity<List>(books, HttpStatus.OK);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/{bookId}", method = RequestMethod.GET)
	public String getBookById(@PathVariable("bookId") String bookId, Model model) throws Exception {

		Book book = bookService.getBookById(Integer.parseInt(bookId));
		model.addAttribute("book", book);
		return "update";
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/librarian", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List> getBooksCreatedByLibrarian(
			@RequestParam(value = "librarianEmail", required = false) String librarianEmail, Model model,
			HttpSession session) throws Exception {

		librarianEmail = (String) session.getAttribute("loggedInUser");
		List<Book> librarianBooks = bookService.getBooksCreatedByLibrarian(librarianEmail);
		return new ResponseEntity<List>(librarianBooks, HttpStatus.OK);
	}

	@RequestMapping(value = "/isbn", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> getBookByISBN(@RequestParam("ISBN") String ISBN) throws Exception {
		System.out.println("hitting seach by ISBN");
		String bookResponse = isbnService.getBookByISBN(ISBN);

		return new ResponseEntity<String>(bookResponse, HttpStatus.OK);
	}

	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/waitlist/{bookId}", method = RequestMethod.GET)
	public void addToWaitlist(@PathVariable("bookId") String bookId, HttpSession session) throws Exception {

		String patronEmail = (String) session.getAttribute("loggedInUser");
		bookService.addToWaitlist(Integer.parseInt(bookId), patronEmail);
	}
	
	
	@RequestMapping(value="/renew/{checkoutId}")
	@ResponseStatus(HttpStatus.OK)
	public void renewBook(HttpSession session, @PathVariable("checkoutId") int checkoutId) throws Exception{
		
		if(session.getAttribute("loggedInUser") != null){
		bookService.renewBook(session.getAttribute("loggedInUser").toString(), checkoutId);
		}else
			throw new UserNotSignedInException("Sorry, you need to sign in before you can renew a book.");
		return;
	}
}












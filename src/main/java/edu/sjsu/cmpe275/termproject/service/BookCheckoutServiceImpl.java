package edu.sjsu.cmpe275.termproject.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.termproject.DAO.DAO;
import edu.sjsu.cmpe275.termproject.exceptions.BadUserRequestException;
import edu.sjsu.cmpe275.termproject.exceptions.BookNotFoundException;
import edu.sjsu.cmpe275.termproject.exceptions.UserNotFoundException;
import edu.sjsu.cmpe275.termproject.model.Book;
import edu.sjsu.cmpe275.termproject.model.BookCheckout;
import edu.sjsu.cmpe275.termproject.model.Patron;

@Service
public class BookCheckoutServiceImpl implements BookCheckoutService {

	@Autowired
	DAO dao;
	@Autowired
	BookService bookService;
	@Autowired
	PatronService patronService;
	@Autowired
	MailService mailService;
	@Autowired
	SystemDateService systemDateService;
	
	
	@Override
	public List<BookCheckout> bookCheckout(Integer[] bookIds, String patronEmail) throws Exception {
		
		List<BookCheckout> tempBookCheckouts = new ArrayList<BookCheckout>();
		
		Patron patron = patronService.getPatronByEmail(patronEmail);
		if(DateUtils.isSameDay(patron.getLastCheckoutDate(), systemDateService.getSystemDate())){	
			System.out.println("IN INITIAL DAILY CHECK");
			if ((patron.getLastCheckoutBookCount() + bookIds.length) > 5) throw new BadUserRequestException("Sorry, you cannot checkout more than 5 books in a day");
		}		
		if ((patron.getCurrentlyCheckoutBooks() + bookIds.length) > 10){
			System.out.println("MORE THAN 10 BOOKS");
			throw new BadUserRequestException("Sorry, you cannot checkout more than 10 books");
		}
		
		for (int i = 0; i < bookIds.length; i++) {
			System.out.println("validating bookid " + bookIds[i]);
			validateBookCheckout(bookIds[i], patronEmail);
		}

		for (int i = 0; i < bookIds.length; i++) {
			tempBookCheckouts.add(finalizeBookCheckout(bookIds[i], patronEmail));
		}
		
		patron.setBookCheckouts(tempBookCheckouts);		
		mailService.sendCheckoutConfirmation(patron, bookIds);
		
		return tempBookCheckouts;
	}

	public boolean validateBookCheckout(Integer bookId, String patronEmail) throws Exception {

		Book book = bookService.getBookById(bookId);
		if (book == null) throw new BookNotFoundException("The given book could not be found");
		Patron patron = patronService.getPatronByEmail(patronEmail);
		if (patron == null) throw new UserNotFoundException("Patron not found");	
		if(patron.getBookCheckouts().size() != 0){
			List<BookCheckout> checkouts = patron.getBookCheckouts();
			for (Iterator iterator = checkouts.iterator(); iterator.hasNext();) {	
				BookCheckout bookCheckout = (BookCheckout) iterator.next();
				if(bookCheckout.getCheckoutBook().getBookId() == bookId) throw new BadUserRequestException("Book with ID Already Checked out by Patron");					
			}
		}
		
		if (book.getCurrentStatus() == 0) throw new BadUserRequestException("Sorry, book is not available at the Library");
		if (book.getCurrentStatus() == 2 && (!patron.getEmail().equals(book.getReservedForPatron().getEmail()))) throw new BadUserRequestException("Sorry, book is Reserved for another Patron");
		if (patron.getCurrentlyCheckoutBooks() >= 10) throw new BadUserRequestException("You have already checkedout more than 10 books");
		if(DateUtils.isSameDay(patron.getLastCheckoutDate(),systemDateService.getSystemDate())){	
			System.out.println("IN DAILY CHECK");
			if (patron.getLastCheckoutBookCount() >= 5) throw new BadUserRequestException("You have already checkedout more than 5 books today");
		}
		
		return true;
	}

	public BookCheckout finalizeBookCheckout(Integer bookId, String patronEmail) throws Exception {

		Book book = bookService.getBookById(bookId);
		Patron patron = patronService.getPatronByEmail(patronEmail);

		BookCheckout bookCheckout = new BookCheckout();
		bookCheckout.setCheckoutBook(book);
		bookCheckout.setBookPatron(patron);
		bookCheckout.setCheckoutDate(systemDateService.getSystemDate());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(bookCheckout.getCheckoutDate());
		calendar.add(Calendar.DATE, 30);
		bookCheckout.setReturnDate(calendar.getTime());
		book.getBookCheckouts().add(bookCheckout);
		
		book.setCurrentNumberOfCopies(book.getCurrentNumberOfCopies() - 1);
		if (book.getCurrentNumberOfCopies() == 0) book.setCurrentStatus(0);
		
		dao.bookCheckOut(book);

		if(!DateUtils.isSameDay(patron.getLastCheckoutDate(), systemDateService.getSystemDate())){	
			System.out.println("RESETTING PATRON DAILY LIMIT");
			patron.setLastCheckoutBookCount(0);			
		}
		
		patron.setCurrentlyCheckoutBooks(patron.getCurrentlyCheckoutBooks() + 1);
		patron.setLastCheckoutBookCount(patron.getLastCheckoutBookCount() + 1);
		patron.setLastCheckoutDate(systemDateService.getSystemDate());
		
		dao.updatePatron(patron);
		
		return bookCheckout;
	}
	
	@Override
	public List<BookCheckout> getDueBooks(Date startDate, Date endDate) throws Exception {
		List<BookCheckout> dueBooks = dao.getDueBooks(startDate, endDate);
		return dueBooks;
	}
	
	@Override
	public BookCheckout getBookCheckoutById(int checkoutId) {
		BookCheckout bookCheckout = dao.getBookCheckoutById(checkoutId);
		return bookCheckout;
	}

	@Override
	public void renewBook(int bookCheckoutId, Date newReturnDate) {
		dao.renewBook(bookCheckoutId, newReturnDate);
		return;
	}
}










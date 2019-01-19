package edu.sjsu.cmpe275.termproject.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.termproject.DAO.DAO;
import edu.sjsu.cmpe275.termproject.exceptions.BadUserRequestException;
import edu.sjsu.cmpe275.termproject.exceptions.BookNotFoundException;
import edu.sjsu.cmpe275.termproject.exceptions.BookWaitlistedException;
import edu.sjsu.cmpe275.termproject.exceptions.ExistingBookISBN;
import edu.sjsu.cmpe275.termproject.model.Book;
import edu.sjsu.cmpe275.termproject.model.BookCheckout;
import edu.sjsu.cmpe275.termproject.model.Keyword;
import edu.sjsu.cmpe275.termproject.model.Patron;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	DAO dao;
	@Autowired
	PatronService patronService;
	@Autowired
	MailService mailService;
	@Autowired
	SystemDateService systemDateService;
	@Autowired
	BookCheckoutService bookCheckoutService;
	
	final float dayValue = 15000;

	List<BookCheckout> tempBookCheckouts = new ArrayList<BookCheckout>();

	@Override
	public Book getBookById(Integer bookId) {
		Book book = dao.getBookById(bookId);
		return book;
	}

	@Override
	public Book getBookByISBN(String isbn) throws Exception {
		Book book = dao.getBookByISBN(isbn);
		return book;
	}

	@Override
	public Book getBookByName(String searchString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Book addBook(Book book) throws Exception {

		Book temp = dao.getBookByISBN(book.getISBN());
		if (temp != null)
			throw new ExistingBookISBN("ISBN exists");

		dao.addBook(book);
		return book;
	}

	@Override
	public Book updateBook(Book book) throws Exception {

		Book temp = dao.getBookById(book.getBookId());
		if (temp == null)
			throw new BookNotFoundException("book not found");
		Book isbnTemp = dao.getBookByISBN(book.getISBN());
		if (isbnTemp != null && isbnTemp.getBookId() != book.getBookId())
			throw new ExistingBookISBN("ISBN exists");
		if (book.getNumberOfCopies() < book.getCurrentNumberOfCopies())
			throw new BadUserRequestException("Total Number of Copies Cannot be less than Current Number of Copies");
		if (book.getNumberOfCopies() < book.getBookCheckouts().size())
			throw new BadUserRequestException("Total Number of Copies Cannot be less than Checked out Copies");

		// List<Keyword> tempKeywords = temp.getKeywordList();

		// book.setKeywordList(tempKeywords);

		// System.out.println("updated keyword " +
		// temp.getKeywordList().get(0).getKeyword());

		dao.updateBook(book);
		System.out.println("BOOK UPDATED");
		return book;
	}

	@Override
	public boolean deleteBook(Integer bookId) throws Exception {

		Book book = dao.getBookById(bookId);
		if (book == null)
			throw new BookNotFoundException("Book not found");
		if (book.getBookCheckouts().size() > 0)
			throw new BadUserRequestException("Book Cannot be deleted as it is checkout");
		/*if (book.getCurrentStatus() == 0)
			throw new BadUserRequestException("Book Cannot be deleted as it is checkout");*/
		
		book.setReservedForPatron(null);
		dao.updateBook(book);
		
		System.out.println("book deletion updated");
		
		if (dao.deleteBook(book)) {
			System.out.println("BOOK DELETED");
			return true;
		} else
			return false;
	}

	@Override
	public boolean returnBook(Integer[] bookIds, String patronEmail) throws Exception {

		for (int i = 0; i < bookIds.length; i++) {
			System.out.println("returning book id " + bookIds[i]);
			finalizeReturnBook(bookIds[i], patronEmail);
		}

		Patron patron = patronService.getPatronByEmail(patronEmail);
		patron.setBookCheckouts(tempBookCheckouts);
		mailService.sendReturnConfirmation(patron, bookIds);
		return true;
	}

	public boolean finalizeReturnBook(Integer bookId, String patronEmail) throws Exception {
		
		Patron patron = dao.getPatronByEmail(patronEmail);
		Book book = dao.getBookById(bookId);

		tempBookCheckouts.add(dao.getBookCheckout(patronEmail, bookId));

		dao.returnBook(patronEmail, bookId);
		patron.setCurrentlyCheckoutBooks(patron.getCurrentlyCheckoutBooks() - 1);
		dao.updatePatron(patron);		
		System.out.println("BOOK RETURNED with id " + book.getBookId());
		
		
		book.setCurrentNumberOfCopies(book.getCurrentNumberOfCopies() + 1);
		book.setCurrentStatus(1);
		
		if(book.getWaitList() != null && book.getWaitList().size() != 0){
			
			List<Patron> waitList = book.getWaitList();
			Patron reserveForPatron = null;
			if(waitList.iterator().hasNext()) reserveForPatron = waitList.iterator().next();
			System.out.println("waitlist size of patron " + waitList.size());
				
			for (Iterator iterator = waitList.iterator(); iterator.hasNext();) {
				Patron patron2 = (Patron) iterator.next();
				iterator.remove();
				break;				
			}
			
			System.out.println("waitlist size of patron AFTER " + waitList.size());
						
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(systemDateService.getSystemDate());
			calendar.add(Calendar.DATE, 3);
			
			book.setWaitList(waitList);
			book.setReservedForPatron(reserveForPatron);	
			book.setReservedUntil(calendar.getTime());	
			book.setCurrentStatus(2);
				
			mailService.sendReservationReminder(reserveForPatron, book);
		}
			
		dao.updateBook(book);
		
		return true;
	}

	@Override
	public List<Book> searchBook(String searchString) {

		List<Keyword> keywords = dao.searchBook(searchString);
		List<Book> searchedBooks = new ArrayList<Book>();
		System.out.println("SEARCHED BOOK COUNT " + keywords.size());

		for (Keyword keyword : keywords) {
			Set<Book> books = keyword.getBooks();
			for (Book book : books) {
				System.out.println("s books are " + book.getTitle());
				searchedBooks.add(book);
			}
		}

		return searchedBooks;
	}

	@Override
	public List<Book> getBooksCreatedByLibrarian(String librarianEmail) {

		List<Book> librarianBooks = dao.getBooksCreatedByLibrarian(librarianEmail);
		return librarianBooks;
	}

	@Override
	public List<BookCheckout> getBooksCheckedoutByPatron(String patronEmail) {

		List<BookCheckout> checkedoutBooks = dao.getBooksCheckedoutByPatron(patronEmail);

		for (Iterator<BookCheckout> iterator = checkedoutBooks.iterator(); iterator.hasNext();) {
			BookCheckout bookCheckout = (BookCheckout) iterator.next();
			Book book = bookCheckout.getCheckoutBook();
			System.out.println("CHECKOUT BOOK " + book.getBookId() + " " + book.getTitle() + " " + book.getPublisher());
		}
		return checkedoutBooks;
	}

	@Override
	public boolean addToWaitlist(Integer bookId, String patronEmail) throws Exception {

		Book book = dao.getBookById(bookId);
		Patron patron = dao.getPatronByEmail(patronEmail);

		List<Patron> waitList = book.getWaitList();
		List<BookCheckout> currentBookCheckouts = patron.getBookCheckouts();
		
		if(waitList.contains(patron)) throw new BadUserRequestException("You are already in the waitlist for this book");
		
		for (Iterator iterator = currentBookCheckouts.iterator(); iterator.hasNext();) {
			BookCheckout bookCheckout = (BookCheckout) iterator.next();
			if(bookCheckout.getCheckoutBook().equals(book)) throw new BadUserRequestException("You have already checkout this book");
		}
		
		waitList.add(patron);		
		book.setWaitList(waitList);		
		
		dao.updateBook(book);
		return false;
	}
	
	@Override
	public Integer getTotalFine(String patronId) {
		
		List<BookCheckout> bookCheckouts = getBooksCheckedoutByPatron(patronId);
		Integer totalFine = 0;
		for(int i=0; i<bookCheckouts.size();i++){
			if(systemDateService.getSystemDate().after(bookCheckouts.get(i).getReturnDate())){
				Float temp = ((float) ((Math.abs(systemDateService.getSystemDate().getTime() - bookCheckouts.get(i).getReturnDate().getTime()))/(60 * 60 * 1000)))/24;
				//Float temp = (float)(systemDateService.getSystemDate().getTime() - bookCheckouts.get(i).getReturnDate().getTime());
				//temp = temp / dayValue;
				System.out.println("the difference in time is"+temp);
				System.out.println("the ceiling value is: "+Math.ceil(temp));
				if(temp == 0)
					totalFine++;
				
				totalFine = (int) (totalFine +  Math.ceil(temp));
			}
		}
		return totalFine;
	}

	@Override
	public void renewBook(String email, int checkoutId) throws Exception {
		
		BookCheckout bookCheckout = bookCheckoutService.getBookCheckoutById(checkoutId);
		Float temp =(float) Math.abs(bookCheckout.getReturnDate().getTime() 
				- bookCheckout.getCheckoutDate().getTime())/(60 * 60 * 1000)/24;
		
		System.out.println("The diff is: "+temp);
		
		if(bookCheckout.getCheckoutBook().getWaitList().size() == 0 && (temp<76)){
		
			System.out.println("Trying to renew book");
			Calendar c = Calendar.getInstance();
			c.setTime(bookCheckout.getReturnDate());
			c.add(Calendar.DAY_OF_MONTH, 30);
			bookCheckoutService.renewBook(checkoutId, c.getTime());
		}else{
			System.out.println("Can't renew book");
			throw new BookWaitlistedException("Sorry, the book is waitlisted. You can't renew it.");
			
		}
	}

	@Override	
	public List<Book> getReservedBooks() {
		List<Book> reservedBooks = dao.getReservedBooks();
		return reservedBooks;
	}		
}
















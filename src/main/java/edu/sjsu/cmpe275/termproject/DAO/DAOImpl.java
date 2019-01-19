package edu.sjsu.cmpe275.termproject.DAO;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.sjsu.cmpe275.termproject.model.Book;
import edu.sjsu.cmpe275.termproject.model.BookCheckout;
import edu.sjsu.cmpe275.termproject.model.Keyword;
import edu.sjsu.cmpe275.termproject.model.Librarian;
import edu.sjsu.cmpe275.termproject.model.Patron;
import edu.sjsu.cmpe275.termproject.model.SystemDate;
import edu.sjsu.cmpe275.termproject.repository.BookCheckoutRepository;
import edu.sjsu.cmpe275.termproject.repository.BookRepository;
import edu.sjsu.cmpe275.termproject.repository.DateRepository;
import edu.sjsu.cmpe275.termproject.repository.KeywordRepository;
import edu.sjsu.cmpe275.termproject.repository.LibrarianRepository;
import edu.sjsu.cmpe275.termproject.repository.PatronRepository;

@Service
public class DAOImpl implements DAO {

	@Autowired
	BookRepository bookRepository;
	@Autowired
	LibrarianRepository librarianRepository;
	@Autowired
	PatronRepository patronRepository;
	@Autowired
	BookCheckoutRepository bookCheckoutRepository;
	@Autowired
	KeywordRepository keywordRepository;
	@Autowired
	DateRepository dateRepository;

	@Override
	public Patron getPatronByEmail(String email) {

		Patron patron = patronRepository.findOne(email);
		return patron;
	}

	@Override
	@Transactional
	public Patron createPatron(Patron patron) {
		patronRepository.save(patron);
		return patron;
	}
	
	
	@Override
	@Transactional
	public boolean updatePatron(Patron patron) {
		patronRepository.save(patron);
		return true;
	}

	@Override
	public Patron getPatronBySjsuId(String sjsuId) {
		Patron patron = patronRepository.findPatronBySjsuId(sjsuId);
		return patron;
	}
	
	@Override
	public boolean setPatronVerifiedFlag(String email) {
		patronRepository.setPatronVerifiedFlag(email);
		return true;	
	}
	
	@Override
	public List<BookCheckout> getBooksCheckedoutByPatron(String patronEmail) {
		List<BookCheckout> ch = bookCheckoutRepository.findBooksCheckedoutByPatron(patronEmail);
		System.out.println("FROM QUERY " + ch.size());
		return ch;
	}

	// ************************************************************
	// LIBRARIAN
	// ********************************************************

	@Override
	public Librarian getLibrarianByEmail(String email) {
		Librarian librarian = librarianRepository.findOne(email);
		return librarian;	
	}

	@Override
	public Librarian createLibrarian(Librarian librarian) {
		librarianRepository.save(librarian);
		return librarian;
	}

	@Override
	public Librarian getLibrarianBySjsuId(String sjsuId) {
		Librarian librarian = librarianRepository.findLibrarianBySjsuId(sjsuId);
		return librarian;
	}

	@Override
	public List<Librarian> getAllLibrarians() {
		List<Librarian> librarians = librarianRepository.findAll();
		return librarians;
	}

	@Override
	public boolean setLibrarianVerifiedFlag(String email) {
		librarianRepository.setLibrarianVerifiedFlag(email);	
		return true;	
	}
	
	
	// ************************************************************
	// BOOK
	// ************************************************************

	@Override
	public Book getBookById(Integer bookId) {
		Book book = bookRepository.findOne(bookId);
		return book;
	}

	@Override
	public Book getBookByName(String searchString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addBook(Book book) {
		bookRepository.save(book);
		return true;
	}

	@Override
	public boolean updateBook(Book book) {
		//bookRepository.save(book);
		bookRepository.saveAndFlush(book);
		return true;
	}

	@Override
	public boolean deleteBook(Book book) {
		bookRepository.delete(book);
		return true;
	}

	@Override
	public Book getBookByISBN(String isbn) {
		Book book = bookRepository.findBookByISBN(isbn);
		return book;
	}
	
	@Override
	public List<Book> getBooksCreatedByLibrarian(String librarianEmail) {

		List<Book> librarianBooks = bookRepository.findBookByLibrarian(librarianEmail);
		return librarianBooks;
	}
	
	
	@Override
	public BookCheckout getBookCheckout(String patronEmail, Integer bookId){
		
		BookCheckout bookCheckout = bookCheckoutRepository.getBookCheckout(patronEmail, bookId);
		return bookCheckout;
	}
	
	@Override
	public List<Book> getReservedBooks() {
		
		List<Book> reservedBooks = bookRepository.getReservedBooks();
		return reservedBooks;
	}

	
	// ************************************************************
	// BOOK CHECKOUT AND RETURN
	// ************************************************************

	@Override
	public boolean bookCheckOut(Book book) {
		bookRepository.save(book);
		return true;
	}

	@Override
	public boolean bookReturn(BookCheckout bookCheckout) {
		bookCheckoutRepository.delete(bookCheckout);
		return true;
	}

	@Override
	public boolean returnBook(String patronEmail, Integer bookId) {
		
		bookCheckoutRepository.returnBook(patronEmail, bookId);
		return true;
	}
	

	@Override
	public List<BookCheckout> getDueBooks(Date startDate, Date endDate) {
		List<BookCheckout> dueBooks = bookCheckoutRepository.getDueBooks(startDate, endDate);
		return dueBooks;
	}
	
	@Override
	public BookCheckout getBookCheckoutById(int checkoutId) {
		BookCheckout bookCheckout = bookCheckoutRepository.findOne(checkoutId);
		return bookCheckout;
	}

	@Override
	public void renewBook(int bookCheckoutId, Date newReturnDate) {
		
		//bookCheckoutRepository.renewBook(bookCheckoutId, newReturnDate);
		BookCheckout bookCheckout = bookCheckoutRepository.findOne(bookCheckoutId);
		bookCheckout.setReturnDate(newReturnDate);
		bookCheckoutRepository.save(bookCheckout);
		return;
	}

	
	// ************************************************************
	// Keyword
	// ************************************************************

	@Override
	public void addKeyword(Keyword keyword) {
		keywordRepository.save(keyword);
		System.out.println("I got this!:" + keyword.getKeyword());
	}

	@Override
	public void removeKeyword(Integer bookId) {

		keywordRepository.delete(bookId);
	}

	/*
	 * @Override public List<Keyword> findBooksWithKeyword(String keyword) {
	 * List<Keyword> keywords = keywordRepository.findBooksWithKeyword(keyword);
	 * return keywords; }
	 */

	public List<Keyword> searchBook(String searchString) {

		List<Keyword> keywords = keywordRepository.findKeyword(searchString);
		return keywords;
	}

	@Override
	public void updateKeyword(Keyword keyword) {
		// TODO Auto-generated method stub
		keywordRepository.save(keyword);
		System.out.println("I got this!:" + keyword.getKeyword());
	}

	// ************************************************************
	// Date
	// ************************************************************

	
	@Override
	public Date getSystemDate() {
		List<SystemDate> systemDates = dateRepository.findAll();
		if(systemDates.size()==0){
			Date systemDate = new Date();
			setSystemDate(systemDate);
			return systemDate;
		}else{
		return systemDates.get(0).getSystemDate();
		}
	}

	@Override
	public void setSystemDate(Date date) {
		List<SystemDate> systemDates =dateRepository.findAll();
		SystemDate systemDate = new SystemDate();

		if(systemDates.size() == 0){
		systemDate.setSystemDate(date);
		dateRepository.save(systemDate);
		return;
		}
		systemDate.setId(systemDates.get(0).getId());
		systemDate.setSystemDate(date);
		dateRepository.save(systemDate);
		
	}

	
	

}











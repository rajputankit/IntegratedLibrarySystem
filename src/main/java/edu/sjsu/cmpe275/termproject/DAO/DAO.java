package edu.sjsu.cmpe275.termproject.DAO;

import java.util.Date;
import java.util.List;

import edu.sjsu.cmpe275.termproject.model.Book;
import edu.sjsu.cmpe275.termproject.model.BookCheckout;
import edu.sjsu.cmpe275.termproject.model.Keyword;
import edu.sjsu.cmpe275.termproject.model.Librarian;
import edu.sjsu.cmpe275.termproject.model.Patron;

public interface DAO {

	//PATRON
	Patron getPatronByEmail(String email);
	Patron createPatron(Patron patron);
	boolean updatePatron(Patron patron);
	Patron getPatronBySjsuId(String sjsuId);	 
	boolean setPatronVerifiedFlag(String email);
	List<BookCheckout> getBooksCheckedoutByPatron(String patronEmail);	

			
	//LIBRARIAN
	Librarian getLibrarianByEmail(String email);
	Librarian createLibrarian(Librarian librarian);	
	Librarian getLibrarianBySjsuId(String sjsuId);	
	List<Librarian> getAllLibrarians();
	boolean setLibrarianVerifiedFlag(String email);
	
	
	//BOOK
	Book getBookById(Integer bookId);	
	Book getBookByName(String searchString);
	Book getBookByISBN(String isbn);
	boolean addBook(Book book);
	boolean updateBook(Book book);
	boolean deleteBook(Book book);
	List<Book> getBooksCreatedByLibrarian(String librarianEmail);
	//+1
	List<Book> getReservedBooks();
	
	
	//BOOK CHECKOUT
	boolean bookCheckOut(Book book);	
	boolean bookReturn(BookCheckout bookCheckout);
	boolean returnBook(String patronEmail, Integer bookId);
	BookCheckout getBookCheckout(String patronEmail, Integer bookId);
	List<BookCheckout> getDueBooks(Date startDate, Date endDate);
	//+1
	BookCheckout getBookCheckoutById(int checkoutId);
	void renewBook(int bookCheckoutId, Date newReturnDate);
	

	//Keyword
	void addKeyword(Keyword keyword);
	void updateKeyword(Keyword keyword);
	void removeKeyword(Integer bookId);
	List<Keyword> searchBook(String searchString);
	//List<Keyword> findBooksWithKeyword(String keyword);
	
	//Date
	Date getSystemDate();
	void setSystemDate(Date date);
}

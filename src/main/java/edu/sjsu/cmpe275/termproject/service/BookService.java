package edu.sjsu.cmpe275.termproject.service;

import java.util.List;

import edu.sjsu.cmpe275.termproject.model.Book;
import edu.sjsu.cmpe275.termproject.model.BookCheckout;

public interface BookService {

	Book getBookById(Integer bookId) throws Exception;	
	Book getBookByName(String searchString) throws Exception;
	Book getBookByISBN(String isbn) throws Exception;
	Book addBook(Book book) throws Exception;	
	Book updateBook(Book book) throws Exception;
	boolean deleteBook(Integer bookId) throws Exception;
	boolean returnBook(Integer[] bookIds, String patronEmail) throws Exception;	
	List<Book> searchBook(String searchString);
	List<Book> getBooksCreatedByLibrarian(String librarianEmail);
	List<BookCheckout> getBooksCheckedoutByPatron(String patronEmail);
	Integer getTotalFine(String patronId);
	boolean addToWaitlist(Integer bookId, String patronEmail) throws Exception;
	void renewBook(String email, int checkoutId) throws Exception;
	List<Book> getReservedBooks();
}

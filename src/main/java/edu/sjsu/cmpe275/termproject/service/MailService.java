package edu.sjsu.cmpe275.termproject.service;

import java.util.List;

import edu.sjsu.cmpe275.termproject.model.Book;
import edu.sjsu.cmpe275.termproject.model.BookCheckout;
import edu.sjsu.cmpe275.termproject.model.Librarian;
import edu.sjsu.cmpe275.termproject.model.Patron;

public interface MailService {

	void sendVerificationCode(Patron patron) throws Exception;
	void sendLibrarianVerificationCode(Librarian librarian) throws Exception;
	void sendRegistrationConfirmation(String email) throws Exception;
	void sendReturnConfirmation(Patron patron, Integer[] tempbookIds) throws Exception;
	void sendCheckoutConfirmation(Patron patron, Integer[] tempbookIds) throws Exception;
	void sendDueDateReminder(List<BookCheckout> dueBooks) throws Exception;
	void sendReservationReminder(Patron patron, Book book) throws Exception;
}

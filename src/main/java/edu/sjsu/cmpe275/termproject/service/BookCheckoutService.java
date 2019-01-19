package edu.sjsu.cmpe275.termproject.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import edu.sjsu.cmpe275.termproject.model.BookCheckout;

public interface BookCheckoutService {

	List<BookCheckout> bookCheckout(Integer[] bookIds, String patronEmail) throws Exception;
	List<BookCheckout> getDueBooks(Date startDate, Date endDate) throws Exception;
	//+2
	BookCheckout getBookCheckoutById(int checkoutId);
	void renewBook(int bookCheckoutId, Date newReturnDate);

}

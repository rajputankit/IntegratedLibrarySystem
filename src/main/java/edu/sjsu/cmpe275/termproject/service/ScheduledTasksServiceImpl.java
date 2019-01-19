package edu.sjsu.cmpe275.termproject.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import edu.sjsu.cmpe275.termproject.model.Book;
import edu.sjsu.cmpe275.termproject.model.BookCheckout;
import edu.sjsu.cmpe275.termproject.model.Patron;

@Component
public class ScheduledTasksServiceImpl implements ScheduledTasksService {

	@Autowired
	BookCheckoutService bookCheckoutService;
	@Autowired
	MailService mailService;
	@Autowired
	SystemDateService systemDateService;
	@Autowired
	BookService bookService;

	@Scheduled(fixedRate = 1000 * 60 * 60 * 24)
	@Async
	public void getDueBooks() {

		Date todayDate = systemDateService.getSystemDate();
		Date endDate = setEndDate(todayDate);

		try {

			List<BookCheckout> dueBooks = bookCheckoutService.getDueBooks(todayDate, endDate);
			mailService.sendDueDateReminder(dueBooks);
		} catch (Exception E) {
			E.printStackTrace();
		}

	}

	public Date setEndDate(Date todayDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(todayDate);
		c.add(Calendar.DAY_OF_MONTH, 5);
		Date endDate = c.getTime();
		return endDate;
	}

	@Override
	@Async
	@Scheduled(fixedRate = 1000 * 60 * 5)
	public void keepTime() throws Exception {
		
		Date systemDate = systemDateService.getSystemDate();
		Calendar c = Calendar.getInstance();
		c.setTime(systemDate);

		if (systemDate.getMinutes() != (new Date()).getMinutes())
			c.add(Calendar.MINUTE, 5);

		systemDateService.setSystemDate(c.getTime());
		System.out.println("The scheduled system time is: " + c.getTime());
	}

	@Async
	@Scheduled(fixedRate = 1000 * 60 * 60 * 24)
	public void checkReservedBooks() throws Exception {
		List<Book> reservedBooks = bookService.getReservedBooks();
		System.out.println("The number of reserved books is: " + reservedBooks.size());

		for (int i = 0; i < reservedBooks.size(); i++) {
			
			Book book = reservedBooks.get(i);
			Date reservedUntil = book.getReservedUntil();
			System.out.println("resereved untul " + reservedUntil.toString());
			System.out.println("sys date " + systemDateService.getSystemDate());
			if (reservedUntil.before(systemDateService.getSystemDate())) {
				
				System.out.println("The book reservation is being cancelled");
					
				List<Patron> waitList = book.getWaitList();
				Patron reserveForPatron = null;
				for (Iterator iterator = waitList.iterator(); iterator.hasNext();) {
					reserveForPatron = (Patron) iterator.next();
					book.setReservedForPatron(reserveForPatron);
					iterator.remove();
					book.setWaitList(waitList);
					bookService.updateBook(book);
					break;
				}				
			}
		}
	}
}















package edu.sjsu.cmpe275.termproject.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.termproject.model.Book;
import edu.sjsu.cmpe275.termproject.model.BookCheckout;
import edu.sjsu.cmpe275.termproject.model.Librarian;
import edu.sjsu.cmpe275.termproject.model.Patron;

@Service
public class MailServiceImpl implements MailService {

	private JavaMailSender javaMailSender;

	@Autowired
	public MailServiceImpl(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@Autowired
	PatronService patronService;

	@Async
	@Override
	public void sendVerificationCode(Patron patron) throws Exception {
		// TODO Auto-generated method stub
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		
		mimeMessage.setFrom(new InternetAddress("CmpE275 Library Management System  <vadwala93@gmail.com>"));
		mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(patron.getEmail()));
		mimeMessage.setSubject("Please verify your library account");
		mimeMessage.setText("Welcome to the library! Your verification code is " + patron.getVerificationCode());
		
		javaMailSender.send(mimeMessage);

		System.out.println("Email Sent!");
	}
	

	@Async
	@Override
	public void sendLibrarianVerificationCode(Librarian librarian) throws Exception{
		// TODO Auto-generated method stub
	
		MimeMessage mail = javaMailSender.createMimeMessage();
		
		mail.setRecipient(Message.RecipientType.TO, new InternetAddress(librarian.getSjsuEmail()));
		mail.setFrom(new InternetAddress("CmpE275 Library Management System  <vadwala93@gmail.com>"));
		mail.setSubject("Please verify your library account");
		mail.setText("Welcome to the library! Your verification code is " + librarian.getVerificationCode());
		
		javaMailSender.send(mail);

		System.out.println("Email Sent!");
	}

	@Async
	@Override
	public void sendRegistrationConfirmation(String email) throws Exception {

		MimeMessage mail = javaMailSender.createMimeMessage();

		mail.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
		mail.setFrom(new InternetAddress("CmpE275 Library Management System  <vadwala93@gmail.com>"));
		mail.setSubject("Welcome to the library!");
		mail.setText("Account Verified." + " Welcome to the library.");
		
		javaMailSender.send(mail);
		System.out.println("Email Sent!");
	}

	@Async
	@Override
	public void sendCheckoutConfirmation(Patron patron, Integer[] tempbookIds) throws Exception{

		List<BookCheckout> allUserBookCheckouts = (List<BookCheckout>) patron.getBookCheckouts();
		List<Integer> bookIds = Arrays.asList(tempbookIds);

		/*
		 * for(Iterator<Book> i = books.iterator(); i.hasNext(); ) { Book book =
		 * i.next(); bookIds.add(book.getBookId()); }
		 */

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < allUserBookCheckouts.size(); i++) {

			if (bookIds.contains(allUserBookCheckouts.get(i).getCheckoutBook().getBookId())) {
				String bookTitle = allUserBookCheckouts.get(i).getCheckoutBook().getTitle();
				Date checkOutDate = allUserBookCheckouts.get(i).getCheckoutDate();
				Date dueDate = allUserBookCheckouts.get(i).getReturnDate();
				sb.append("The book " + bookTitle + " checked out on " + checkOutDate + " is due on " + dueDate);
				sb.append("																			\n\n");
			}
		}

		System.out.println("CONFIRMATION CONTENT " + sb.toString());


		MimeMessage mail = javaMailSender.createMimeMessage();

		mail.setRecipient(Message.RecipientType.TO, new InternetAddress(patron.getEmail()));
		mail.setFrom(new InternetAddress("CmpE275 Library Management System  <vadwala93@gmail.com>"));
		mail.setSubject("Books checked out");
		mail.setText(sb.toString());
		
		javaMailSender.send(mail);
		System.out.println("Email Sent!");

	}

	@Async
	@Override
	public void sendReturnConfirmation(Patron patron, Integer[] tempbookIds) throws Exception {

		List<BookCheckout> allUserBookCheckouts = (List<BookCheckout>) patron.getBookCheckouts();
		List<Integer> bookIds = Arrays.asList(tempbookIds);

		/*
		 * for(Iterator<Book> i = books.iterator(); i.hasNext(); ) { Book book =
		 * i.next(); bookIds.add(book.getBookId()); }
		 */

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < allUserBookCheckouts.size(); i++) {

			if (bookIds.contains(allUserBookCheckouts.get(i).getCheckoutBook().getBookId())) {
				String bookTitle = allUserBookCheckouts.get(i).getCheckoutBook().getTitle();

				sb.append("The book " + bookTitle + " RETURNED  on " + (new Date()).toString());
				sb.append("																			\n\n\n");
			}
		}

		System.out.println("RETURN CONTENT " + sb.toString());
		
		MimeMessage mail = javaMailSender.createMimeMessage();

		mail.setRecipient(Message.RecipientType.TO, new InternetAddress(patron.getEmail()));
		mail.setFrom(new InternetAddress("CmpE275 Library Management System  <vadwala93@gmail.com>"));
		mail.setSubject("Books Returned");
		mail.setText(sb.toString());
		
		javaMailSender.send(mail);
		System.out.println("Email Sent!");

	}

	@Override
	@Async
	public void sendDueDateReminder(List<BookCheckout> dueBooks) throws Exception{
		for(int i=0; i<dueBooks.size();i++){
			Patron patron = dueBooks.get(i).getBookPatron();
			BookCheckout bookCheckout = dueBooks.get(i);
			System.out.println("Sending reminder for: "+bookCheckout.getCheckoutBook().getTitle());

			MimeMessage mail = javaMailSender.createMimeMessage();

			mail.setRecipient(Message.RecipientType.TO, new InternetAddress(patron.getEmail()));
			mail.setFrom(new InternetAddress("CmpE275 Library Management System  <vadwala93@gmail.com>"));
			mail.setSubject("Book due");
			mail.setText("The book: "+bookCheckout.getCheckoutBook().getTitle()+"is due on: "+bookCheckout.getReturnDate() + "\n\n");
			
			javaMailSender.send(mail);
			System.out.println("Email Sent!");
		
		}
	}

	
	@Override
	@Async
	public void sendReservationReminder(Patron patron, Book book) throws Exception{
		
		MimeMessage mail = javaMailSender.createMimeMessage();

		mail.setRecipient(Message.RecipientType.TO, new InternetAddress(patron.getEmail()));
		mail.setFrom(new InternetAddress("CmpE275 Library Management System  <vadwala93@gmail.com>"));
		mail.setSubject("Book Reserved for you");
		mail.setText("The book: "+ book.getTitle() + " is available and reserved for you until: "+ book.getReservedUntil() + ". Please login and checkout the book before " + book.getReservedUntil() + ", else it will be issued to the next person in the waitlist");
		
		javaMailSender.send(mail);
		System.out.println("Email Sent!");		
	}

}

package edu.sjsu.cmpe275.termproject.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
public class BookCheckout {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int checkoutId;
	
	private Date checkoutDate;
	private Date returnDate;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade={CascadeType.MERGE})
	@JoinColumn(name="bookId")
	private Book checkoutBook;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)	
	@JoinColumn(name="patronId")
	private Patron checkoutPatron;
	
	public int getCheckoutId() {
		return checkoutId;
	}
	
	public void setCheckoutId(int checkoutId) {
		this.checkoutId = checkoutId;
	}

	public Book getCheckoutBook() {
		return checkoutBook;
	}

	public void setCheckoutBook(Book checkoutBook) {
		this.checkoutBook = checkoutBook;
	}

	public Patron getBookPatron() {
		return checkoutPatron;
	}

	public void setBookPatron(Patron bookPatron) {
		this.checkoutPatron = bookPatron;
	}

	public Date getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(Date checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public Patron getCheckoutPatron() {
		return checkoutPatron;
	}

	public void setCheckoutPatron(Patron checkoutPatron) {
		this.checkoutPatron = checkoutPatron;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
}
